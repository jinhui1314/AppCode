package cc.yuan.leopardkit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import cc.yuan.leopardkit.db.HttpDbUtil;
import cc.yuan.leopardkit.download.DownLoadManager;
import cc.yuan.leopardkit.download.model.DownloadInfo;
import cc.yuan.leopardkit.http.LeopardClient;
import cc.yuan.leopardkit.http.base.BaseEnetity;
import cc.yuan.leopardkit.http.base.HttpMethod;
import cc.yuan.leopardkit.http.factory.CacheFactory;
import cc.yuan.leopardkit.http.factory.RequestJsonFactory;
import cc.yuan.leopardkit.http.factory.UploadFileFactory;
import cc.yuan.leopardkit.interfaces.FileRespondResult;
import cc.yuan.leopardkit.interfaces.HttpRespondResult;
import cc.yuan.leopardkit.interfaces.IProgress;
import cc.yuan.leopardkit.upload.FileUploadEnetity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yuan on 2016/9/1.
 * Detail 自动化构建请求对象 开发者也可以自定义
 */
public class LeopardHttp {

    private static final String TAG = "LeopardHttp";

    private static int HANDER_DELAYED_TIME = 500;
    private static String ADDRESS = "http://127.0.0.1";

    private static boolean useCache = false;

    /**
     * 用之前必须先初始化主机域名
     *
     * @param address
     */
    public static void init(String address, Context context) {
        ADDRESS = address;
        HttpDbUtil.initHttpDB(context.getApplicationContext());
    }

    private static LeopardClient.Builder getBuilder(Context mC) {
        LeopardClient.Builder builder = new LeopardClient.Builder()
                .addGsonConverterFactory(GsonConverterFactory.create())
                .addRxJavaCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .baseUrl(ADDRESS);

        if (useCache && mC != null) {
            builder.addCacheFactory(CacheFactory.create(mC));//离线缓存
        }

        return builder;
    }

    /**
     * 是否使用缓存
     *
     * @param cache
     */
    public static void setUseCache(boolean cache) {
        useCache = cache;
    }

    /**
     * 提供不需要自定义头部入口
     *
     * @param type
     * @param httpRespondResult
     */
    public static void SEND(HttpMethod type, Context context, String url, Map<String, Object> map, HttpRespondResult httpRespondResult) {
        if (type.toString().equals(HttpMethod.GET.toString())) {
            GET(context, url, map, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.GET_JSON.toString())) {
            GETjson(context, url, map, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST.toString())) {
            POST(context, url, map, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST_JSON.toString())) {
            POSTJson(context, url, map, httpRespondResult);
        } else {
            Log.i(TAG, "Type unknown");
        }
    }

    /**
     * 提供不需要自定义头部入口
     *
     * @param type
     * @param enetity
     * @param httpRespondResult
     */
    @Deprecated
    public static void SEND(HttpMethod type, Context context, BaseEnetity enetity, HttpRespondResult httpRespondResult) {
        if (type.toString().equals(HttpMethod.GET.toString())) {
            GET(context, enetity, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.GET_JSON.toString())) {
            GETjson(context, enetity, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST.toString())) {
            POST(context, enetity, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST_JSON.toString())) {
            POSTJson(context, enetity, httpRespondResult);
        } else {
            Log.i(TAG, "Type unknown");
        }
    }

    /**
     * 下载入口
     *
     * @param downloadInfo
     * @param iProgress
     * @return 拥有Task的下载实体
     */
    public static DownloadInfo DWONLOAD(final DownloadInfo downloadInfo, final IProgress iProgress) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (downloadInfo.getState() != DownLoadManager.STATE_WAITING)
                    iProgress.onProgress(msg.arg1, msg.arg2, msg.arg1 >= msg.arg2);
            }
        };

        DownLoadManager.getManager().addTask(downloadInfo, new FileRespondResult() {
            @Override
            public void onSuccess(final String content) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iProgress.onSuccess(content);
                    }
                }, HANDER_DELAYED_TIME);
            }

            @Override
            public void onExecuting(long progress, long total, boolean done) {
                Message message = new Message();
                message.arg1 = (int) progress;
                message.arg2 = (int) total;
                handler.sendMessageDelayed(message, HANDER_DELAYED_TIME);
            }
        });

        return downloadInfo;
    }

    public static void UPLOAD(Context context,FileUploadEnetity uploadEnetity, Map<String, String> description, final IProgress iProgress) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                iProgress.onProgress(msg.arg1, msg.arg2, msg.arg1 >= msg.arg2);
            }
        };

        FileRespondResult respondResult = new FileRespondResult() {

            @Override
            public void onSuccess(final String content) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iProgress.onSuccess(content);
                    }
                }, HANDER_DELAYED_TIME);
            }

            @Override
            public void onExecuting(long progress, long total, boolean done) {
                Message message = new Message();
                message.arg1 = (int) progress;
                message.arg2 = (int) total;
                handler.sendMessageDelayed(message, HANDER_DELAYED_TIME);
            }
        };

        getBuilder(null)
                .addUploadFileFactory(UploadFileFactory.create())
                .build()
                .upLoadFiles(context,uploadEnetity, description, respondResult);

    }

    public static void UPLOAD(Context context,FileUploadEnetity uploadEnetity, Map<String, String> description, HashMap<String, String> header, final IProgress iProgress) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                iProgress.onProgress(msg.arg1, msg.arg2, msg.arg1 >= msg.arg2);
            }
        };

        FileRespondResult respondResult = new FileRespondResult() {
            @Override
            public void onSuccess(final String content) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iProgress.onSuccess(content);
                    }
                }, HANDER_DELAYED_TIME);
            }

            @Override
            public void onExecuting(long progress, long total, boolean done) {
                Message message = new Message();
                message.arg1 = (int) progress;
                message.arg2 = (int) total;
                handler.sendMessageDelayed(message, HANDER_DELAYED_TIME);
            }
        };

        getBuilder(null)
                .addUploadFileFactory(UploadFileFactory.create())
                .addHeader(header)
                .build()
                .upLoadFiles(context,uploadEnetity, description, respondResult);

    }

    public static void UPLOADSimple(String type,FileUploadEnetity uploadEnetity, Map<String, String> description, HashMap<String, String> header, final IProgress iProgress) {
        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                iProgress.onProgress(msg.arg1, msg.arg2, msg.arg1 >= msg.arg2);
            }
        };

        FileRespondResult respondResult = new FileRespondResult() {
            @Override
            public void onSuccess(final String content) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iProgress.onSuccess(content);
                    }
                }, HANDER_DELAYED_TIME);
            }

            @Override
            public void onExecuting(long progress, long total, boolean done) {
                Message message = new Message();
                message.arg1 = (int) progress;
                message.arg2 = (int) total;
                handler.sendMessageDelayed(message, HANDER_DELAYED_TIME);
            }
        };

        getBuilder(null)
                .addUploadFileFactory(UploadFileFactory.create())
                .addHeader(header)
                .build()
                .upLoadFile(type,uploadEnetity, description, respondResult);
    }

    /**
     * 提供需要自定义头部入口
     *
     * @param type
     * @param context
     * @param header
     * @param httpRespondResult
     */
    public static void SEND(HttpMethod type, Context context, String url, Map<String, Object> map, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        if (type.toString().equals(HttpMethod.GET.toString())) {
            GET(context, url, map, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.GET_JSON.toString())) {
            GETjson(context, url, map, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST.toString())) {
            POST(context, url, map, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST_JSON.toString())) {
            POSTJson(context, url, map, header, httpRespondResult);
        } else {
            Log.i(TAG, "Type unknown");
        }
    }


    /**
     * 提供需要自定义头部入口
     *
     * @param type
     * @param context
     * @param enetity
     * @param header
     * @param httpRespondResult
     */
    @Deprecated
    public static void SEND(HttpMethod type, Context context, BaseEnetity enetity, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        if (type.toString().equals(HttpMethod.GET.toString())) {
            GET(context, enetity, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.GET_JSON.toString())) {
            GETjson(context, enetity, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST.toString())) {
            POST(context, enetity, header, httpRespondResult);
        } else if (type.toString().equals(HttpMethod.POST_JSON.toString())) {
            POSTJson(context, enetity, header, httpRespondResult);
        } else {
            Log.i(TAG, "Type unknown");
        }
    }

    @Deprecated
    private static void POST(Context context, BaseEnetity enetity, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .build()
                .POST(context, enetity, httpRespondResult);
    }

    private static void POST(Context context, String url, Map<String, Object> map, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .build()
                .POST(context, url, map, httpRespondResult);
    }

    private static void POST(Context context, String url, Map<String, Object> map, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .build()
                .POST(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void POST(Context context, BaseEnetity enetity, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .build()
                .POST(context, enetity, httpRespondResult);
    }

    private static void POSTJson(Context context, String url, Map<String, Object> map, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .POST(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void POSTJson(Context context, BaseEnetity enetity, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .POST(context, enetity, httpRespondResult);
    }

    private static void POSTJson(Context context, String url, Map<String, Object> map, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .POST(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void POSTJson(Context context, BaseEnetity enetity, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .POST(context, enetity, httpRespondResult);
    }

    private static void GET(Context context, String url, Map<String, Object> map, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .build()
                .GET(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void GET(Context context, BaseEnetity enetity, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .build()
                .GET(context, enetity, httpRespondResult);
    }

    private static void GET(Context context, String url, Map<String, Object> map, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .build()
                .GET(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void GET(Context context, BaseEnetity enetity, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .build()
                .GET(context, enetity, httpRespondResult);
    }

    private static void GETjson(Context context, String url, Map<String, Object> map, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .GET(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void GETjson(Context context, BaseEnetity enetity, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .GET(context, enetity, httpRespondResult);
    }

    private static void GETjson(Context context, String url, Map<String, Object> map, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .GET(context, url, map, httpRespondResult);
    }

    @Deprecated
    private static void GETjson(Context context, BaseEnetity enetity, HashMap<String, String> header, HttpRespondResult httpRespondResult) {
        getBuilder(context)
                .addHeader(header)
                .addRequestJsonFactory(RequestJsonFactory.create())
                .build()
                .GET(context, enetity, httpRespondResult);
    }

}
