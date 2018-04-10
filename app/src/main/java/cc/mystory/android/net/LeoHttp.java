package cc.mystory.android.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cc.mystory.android.base.BaseBean;
import cc.mystory.android.model.UserBean;
import cc.mystory.android.util.CheckUtil;
import cc.mystory.android.util.PackageInfoTool;
import cc.mystory.android.util.SharePrefHelper;
import cc.yuan.leopardkit.LeopardHttp;
import cc.yuan.leopardkit.download.model.DownloadInfo;
import cc.yuan.leopardkit.http.base.HttpMethod;
import cc.yuan.leopardkit.interfaces.HttpRespondResult;
import cc.yuan.leopardkit.interfaces.IProgress;
import cc.yuan.leopardkit.upload.FileUploadEnetity;


/**
 * Leopard调用
 * <p>
 * Create by Lei on 2016/11/5
 */

public class LeoHttp {
//    private static final String BASE_URL = "http://api.ziyoufang.com/";
    /**
     * 完美单例模式
     */
    private static LeoHttp instance = null;
    private Context context;

    private LeoHttp(Context context) {
        this.context = context;
        LeopardHttp.init(NetApi.BASE_URL, context);
    }

    private static synchronized void syncInit(Context context) {
        if (instance == null)
            instance = new LeoHttp(context);
    }

    public static LeoHttp getInstance(Context context) {
        if (instance == null)
            syncInit(context);

        return instance;
    }

    /**
     * post方法，传入请求url和参数列表，这里默认加上Header，后期如需特殊处理，重写此类即可
     */
    public <T> void post(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<T> callBack) {
        requestHttp(HttpMethod.POST, url, map, clazz, callBack);
    }

    /**
     * post方法，传入请求url和参数列表，这里默认加上Header，后期如需特殊处理，重写此类即可
     */
    public <T> void postList(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<List<T>> callBack) {
        requestHttpList(HttpMethod.POST, url, map, clazz, callBack);
    }

    public <T> void postJson(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<T> callBack) {
        requestHttp(HttpMethod.POST_JSON, url, map, clazz, callBack);
    }

    public <T> void postJsonList(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<List<T>> callBack) {
        requestHttpList(HttpMethod.POST_JSON, url, map, clazz, callBack);
    }

    public <T> void get(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<T> callBack) {
        requestHttp(HttpMethod.GET, url, map, clazz, callBack);
    }

    public <T> void getList(String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<List<T>> callBack) {
        requestHttpList(HttpMethod.GET, url, map, clazz, callBack);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param files
     * @param leoProgress
     */
    public <T> void uploadFiles(String url, List<File> files, Map<String, String> description, final Class<T> clazz, final LeoProgress<T> leoProgress) {
        LeopardHttp.UPLOAD(context,new FileUploadEnetity(url, files), description, generateHeader(), new IProgress() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                if (leoProgress != null)
                    leoProgress.onProgress(progress, total, done);
            }

            @Override
            public void onSuccess(String content) {
                if (leoProgress != null) {
                    manageFileOnSuccess(content, clazz, leoProgress);
                }
            }
        });
    }

    public <T> void uploadFile(String type,String url, File file, Map<String, String> description, final Class<T> clazz, final LeoProgress<T> leoProgress) {
        LeopardHttp.UPLOADSimple(type,new FileUploadEnetity(url, file), description, generateHeader(), new IProgress() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                if (leoProgress != null)
                    leoProgress.onProgress(progress, total, done);
            }

            @Override
            public void onSuccess(String content) {
                if (leoProgress != null) {
                    manageFileOnSuccess(content, clazz, leoProgress);
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url         下载的完整URL
     * @param filePath    文件保存路径
     * @param filename    文件名称
     * @param leoProgress
     */
    public <T> void downLoadFile(String url, String filePath, String filename, final Class<T> clazz, final LeoProgress<T> leoProgress) {
        final DownloadInfo info = new DownloadInfo();
        info.setUrl(url);
        info.setProgress(0L);
        info.setFileSavePath(filePath);
        info.setFileName(filename);

        LeopardHttp.DWONLOAD(info, new IProgress() {
            @Override
            public void onProgress(long progress, long total, boolean done) {

                if (leoProgress != null)
                    leoProgress.onProgress(progress, total, done);

            }

            @Override
            public void onSuccess(String content) {
                if (leoProgress != null) {
                    manageFileOnSuccess(content, clazz, leoProgress);
//                    leoProgress.onSuccess((T) content);
                }
            }
        });
        info.getDownLoadTask().download(false);

    }

    /**
     * 请求网络
     *
     * @param method
     * @param url
     * @param map
     * @param clazz
     * @param callBack
     */
    private <T> void requestHttp(HttpMethod method, String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<T> callBack) {
        LeopardHttp.SEND(method, context, url, map, generateHeader(), new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                Logger.d("onResponce ==============>>>>>>>>>>>" + content);
                manageOnSuccess(content, clazz, callBack);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                manageOnFailure(error, content, callBack);
            }
        });
    }

    /**
     * 请求网络
     *
     * @param method
     * @param url
     * @param map
     * @param clazz
     * @param callBack
     */
    private <T> void requestHttpList(HttpMethod method, String url, Map<String, Object> map, final Class<T> clazz, final LeoCallBack<List<T>> callBack) {
        LeopardHttp.SEND(method, context, url, map, generateHeader(), new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                Logger.d("onResponce ==============>>>>>>>>>>>" + content);
                manageOnSuccessList(content, clazz, callBack);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                manageOnFailure(error, content, callBack);
            }
        });
    }

    /**
     * 返回失败时处理
     *
     * @param error
     * @param content
     * @param callBack
     */
    private <T> void manageOnFailure(Throwable error, String content, LeoCallBack<T> callBack) {
//        callBack.onFailure(1, context.getString(R.string.net_error));
        callBack.onFailure(1, content);

    }

    private <T> void manageOnSuccessList(String content, final Class<T> clazz, LeoCallBack<List<T>> callBack) {
        Gson gson = new GsonBuilder().create();
        BaseBean<Object> baseBean = gson.fromJson(content, new TypeToken<BaseBean<Object>>() {
        }.getType());
        Logger.d("onGsonObj======>>>>" + baseBean.toString());
        if (baseBean.getCode() == 0) {
            JsonParser parser = new JsonParser();
            JsonElement el=null;
            if (content.contains("dataList")) {
                el = parser.parse(gson.toJson(baseBean.getDataList()));
            } else {
                el = parser.parse(gson.toJson(baseBean.getData()));
            }
            if (el.isJsonNull()) {
                callBack.onSuccessEmptyOrNull();
            } else if (el.isJsonArray()) {
                JsonArray jsonArray = el.getAsJsonArray();

                //遍历JsonArray对象
                Iterator it = jsonArray.iterator();
                List<T> list = new ArrayList<>();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    list.add(gson.fromJson(e, clazz));
                }
                callBack.onSuccess(list);
            } else if (el.isJsonPrimitive()) {
                if (gson.fromJson(el, clazz) instanceof String) {
                    String res = gson.fromJson(el, String.class);
                    if (res.length() == 0) {
                        callBack.onSuccessEmptyOrNull();
                    }
                }
                Logger.d("Primitive=======");

            } else if(el.isJsonObject()){
//                callBack.onSuccess(gson.fromJson(el, clazz));

                Logger.d("Other=========");
            }
        } else {
            callBack.onFailure(baseBean.getCode(), baseBean.getMessage());
        }
    }

    private <T> void manageFileOnSuccess(String content, final Class<T> clazz, LeoProgress<T> leoProgress) {
        Gson gson = new GsonBuilder().create();
        BaseBean<Object> baseBean = gson.fromJson(content, new TypeToken<BaseBean<Object>>() {
        }.getType());
        Logger.d("onGsonObj======>>>>" + baseBean.toString());
        if (baseBean.getCode() == 200) {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(gson.toJson(baseBean.getData()));
            if (el.isJsonNull()) {
                leoProgress.onSuccessEmptyOrNull();
                Logger.d("Null=======");
            } else if (el.isJsonObject()) {
                leoProgress.onSuccess(gson.fromJson(el, clazz));
                Logger.d("Object=======");

            } else if (el.isJsonPrimitive()) {
                if (gson.fromJson(el, clazz) instanceof String) {
                    String res = gson.fromJson(el, String.class);
                    if (res.length() == 0) {
                        leoProgress.onSuccessEmptyOrNull();
                    }
                } else {
                    leoProgress.onSuccess(gson.fromJson(el, clazz));
                }
                Logger.d("Primitive=======");

            } else {
                Logger.d("Other=========");
            }

        } else {
            Logger.d("error================>>>>>>>>>>>" + baseBean.getMessage());
            leoProgress.onOtherCode(baseBean.getCode(), baseBean.getMessage());
        }
    }

    /**
     * 返回成功时处理
     *
     * @param content
     * @param clazz
     * @param callBack
     */
    private <T> void manageOnSuccess(String content, final Class<T> clazz, LeoCallBack<T> callBack) {
        Gson gson = new GsonBuilder().create();
        BaseBean<Object> baseBean = gson.fromJson(content, new TypeToken<BaseBean<Object>>() {
        }.getType());
        Logger.d("onGsonObj======>>>>" + baseBean.toString());

        if (baseBean.getCode() == 200) {
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(gson.toJson(baseBean.getData()));
            if (el.isJsonNull()) {
                callBack.onSuccessEmptyOrNull();
                Logger.d("Null=======");
            } else if (el.isJsonObject()) {
                callBack.onSuccess(gson.fromJson(el, clazz));
                Logger.d("Object=======");

            } else if (el.isJsonPrimitive()) {
                if (gson.fromJson(el, clazz) instanceof String) {
                    String res = gson.fromJson(el, String.class);
                    if (res.length() == 0) {
                        callBack.onSuccessEmptyOrNull();
                    }
                } else {
                    callBack.onSuccess(gson.fromJson(el, clazz));
                }
                Logger.d("Primitive=======");

            } else if(el.isJsonObject()){
                callBack.onSuccess(gson.fromJson(el, clazz));

            }else {
                Logger.d("Other=========");
            }
        } else {
            callBack.onFailure(baseBean.getCode(), baseBean.getMessage());
        }
    }

    private HashMap<String, String> generateHeader() {
        HashMap<String, String> headers = new HashMap<>();
        boolean isLogined = CheckUtil.isLogin(context);

        UserBean userBean=(UserBean) SharePrefHelper.getInstance(context).getAsObject(SharePrefHelper.USERBEAN);
        if (isLogined&&userBean!=null) {
            headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6");
            headers.put("X-Requested-With", PackageInfoTool.getInstance(context).getOsVersion() + ";" + PackageInfoTool.getInstance(context).getVersion());
            headers.put("x-access-token", ((UserBean) SharePrefHelper.getInstance(context).getAsObject(SharePrefHelper.USERBEAN)).getAccessToken());
            headers.put("platform", "android");
            headers.put("Connection", "keep-alive");
        } else {
            headers.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6");
            headers.put("X-Requested-With", PackageInfoTool.getInstance(context).getOsVersion() + ";" + PackageInfoTool.getInstance(context).getVersion());
            headers.put("platform", "android");
            headers.put("Connection", "keep-alive");
        }
        return headers;
    }
}
