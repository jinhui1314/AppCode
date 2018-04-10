package cc.mystory.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cc.mystory.android.App;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 检查是否***
 * Create by Lei on 2016/9/20
 */

public class CheckUtil {
    private CheckUtil() throws RuntimeException {
        throw new RuntimeException("此类不能实例化");
    }

    public static final long INTERVAL = 500L;
    private static long lastClickTime = 0L;

    /**
     * 防止按钮的连续点击
     */
    public synchronized static boolean isFastClick() {
        if (lastClickTime == 0) {
            lastClickTime = System.currentTimeMillis();
            return false;
        }
        long time = System.currentTimeMillis();

        if (time - lastClickTime > INTERVAL) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 检查是否登录
     *
     * @return
     */
    public static boolean isLogin(Context context) {
        return SharePrefHelper.getInstance(context)
                .getAsBoolean(SharePrefHelper.LOGINED, false);
    }

    /**
     * 检查是否第一次运行
     *
     * @return
     */
    public static boolean isFirstRun(Context context) {
        return SharePrefHelper.getInstance(context)
                .getAsBoolean(SharePrefHelper.FIRST_RUN, true);
    }

    /**
     * 检查是否是正确的手机号格式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public static boolean isCode(String code) {
        Pattern p = Pattern.compile("(?<![0-9])([0-9]{1,6})(?![0-9])");
        Matcher m = p.matcher(code);

        return m.matches();
    }

    /**
     * 检查是否是正确的邮箱地址
     *
     * @param eMail
     * @return
     */
    public static boolean isEmail(String eMail) {
        Pattern p = Pattern.compile("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
        Matcher m = p.matcher(eMail);

        return m.matches();
    }

    /**
     * 检查是否是正确的用户名
     *
     * @param nickName
     * @return
     */
    public static boolean isNickName(String nickName) {
        Pattern p = Pattern.compile("[A-Za-z0-9_\\-\u4e00-\u9fa5]+");
        Matcher m = p.matcher(nickName);

        return m.matches();
    }

    public static boolean isNetwork() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null)
                return info.isAvailable();
            else
                return false;
        }
    }

    public static boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 是否不锁屏，后期完善，现在返回true
     *
     * @return
     */
    public static boolean keepAwakeWhenControl() {

        String s = SharePrefHelper.getInstance(App.getInstance().getContext()).getAsString(SharePrefHelper.ISLOCK, "true");
        if (s.contains("true"))
            return true;
        else
            return false;
    }

    public static String formatNumber(int playNum) {
        if (playNum < 10000)
            return playNum + "";

        playNum = playNum / 1000;
        DecimalFormat df = new DecimalFormat(".0");
        return df.format(1.0 * playNum / 10)+"万";
    }
}
