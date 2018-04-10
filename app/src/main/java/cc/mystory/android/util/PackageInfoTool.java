package cc.mystory.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import cc.mystory.android.App;
import cc.mystory.android.model.SelfBean;
import cc.mystory.android.model.UserBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * 获取系统信息和应用程序信息
 *
 * @author Lei 2016年7月31日10:34:39
 */

public class PackageInfoTool {

    private PackageInfoTool(Context context) {
        this.context = context;
    }

    static PackageInfoTool infoTool;

    public static PackageInfoTool getInstance(Context context) {
        if (infoTool == null) {
            infoTool = new PackageInfoTool(context);
        }
        return infoTool;
    }

    /**
     * 获取系统的版本号
     *
     * @return Android v
     */
    public static String getOsVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取APP版本号
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取版本信息";
        }
    }



    public String getAppCache() {
        return appCache;
    }

    public String getAppPath() {
        return appPath;
    }

    public PackageInfoTool initAppCacheAndAppPath() {
        if (appCache == null || appPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File t = context.getExternalCacheDir();
                if (t != null)
                    appCache = context.getExternalCacheDir().getAbsolutePath();
                t = context.getExternalFilesDir(null);
                if (t != null)
                    appPath = t.getAbsolutePath();
            }
            if (appCache == null || appCache.isEmpty()) {
                appCache = context.getCacheDir().getAbsolutePath();
                Log.e("setting", "cache file is null");
            }
            if (appPath == null || appPath.isEmpty()) {
                appPath = context.getFilesDir().getAbsolutePath();
                Log.e("setting", "path file is null");
            }
        }

        return infoTool;
    }


    private static String mDeviceName;
    private static Map<String,String> mHeader=new HashMap<>();
    private static Context context;
    private static float mScreenAbsoluteRatio = -1;
    private static float mScreenRatio = -1;
    private static SharedPreferences userSharedPreference;
    private static String appCache;
    private static String appPath;
    private static final String USER_PREFERENCE = "userData";
    private static final String USER_INFO_DATA = "userInfoData";
    private static SelfBean mSelfInfo;
    private static boolean settingNull = false;
    private static String mVersionName;
    private static final String HEADER_DEVICE = "X-Requested-With";
    private static final String HEADER_ACCESS_TOKEN = "x-access-token";
    /**
     * 设置用户数据
     * @param selfBean
     */
    public static void putUserInfo(UserBean selfBean){


        final String tempAccess = selfBean.getAccessToken();
        if (tempAccess != null && !tempAccess.isEmpty())
            setAccessToken(tempAccess);
        saveUserSettings();
    }

    /**
     * 设置accessToken
     * @param accessToken
     */
    public static void setAccessToken(String accessToken){
        if (mHeader.size() != 0) {
            mHeader.remove(HEADER_ACCESS_TOKEN);
        }else {
            mHeader.put(HEADER_DEVICE,mDeviceName);
        }
        mHeader.put(HEADER_ACCESS_TOKEN, accessToken);
    }
    /**
     * 初始化设置
     * @param c
     */
    public static void initSetting(Context c){
        if (mHeader == null)
            mHeader = new HashMap<String,String>();
        context = c;
        if (mScreenAbsoluteRatio == -1){
            mScreenAbsoluteRatio = -1;
            mScreenRatio = -1;
        }
        if (userSharedPreference == null)
            userSharedPreference = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        if (appCache == null || appPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File t = c.getExternalCacheDir();
                if ( t != null)
                    appCache = c.getExternalCacheDir().getAbsolutePath();
                t = c.getExternalFilesDir(null);
                if ( t != null)
                    appPath = t.getAbsolutePath();
            }
            if (appCache == null || appCache.isEmpty()){
                appCache = context.getCacheDir().getAbsolutePath();
                Log.e("setting","cache file is null");
            }
            if (appPath == null || appPath.isEmpty()){
                appPath = context.getFilesDir().getAbsolutePath();
                Log.e("setting","path file is null");
            }
        }
        initSettings();
    }
    /**
     * 返回设备名称
     * @return
     */
    public static String getPlatform(){
        if (mDeviceName == null)
            initSetting(App.getInstance().getContext());
        return mDeviceName;
    }
    /**
     * 保存用户数据
     */
    private static void saveUserSettings(){
        settingNull = false;
        SharePrefHelper.getInstance(context).put(USER_INFO_DATA,mSelfInfo);
    }
    /**
     * 初始化设置
     */
    private static void initSettings(){
        mSelfInfo = (SelfBean) SharePrefHelper.getInstance(context).getAsObject(USER_INFO_DATA);


        if (mDeviceName == null)
            mDeviceName = "Android "+ Build.VERSION.RELEASE;

        String access = null;
        if (mSelfInfo != null)
            access = mSelfInfo.getAccessToken();

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            mVersionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            mVersionName = "null";
            e.printStackTrace();
        }
        String deviceAndPackage = mDeviceName +";"+mVersionName;

        mHeader.put(HEADER_DEVICE,deviceAndPackage);

        if (access != null && !access.isEmpty())
            mHeader.put(HEADER_ACCESS_TOKEN,access);
        else
            settingNull = true;
    }
}
