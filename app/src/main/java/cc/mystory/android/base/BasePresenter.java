package cc.mystory.android.base;

import android.content.Context;

import cc.mystory.android.net.LeoCallBack;
import cc.mystory.android.net.LeoHttp;
import cc.mystory.android.net.LeoProgress;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Create by Lei on 2016/11/25
 */

public abstract class BasePresenter<D extends IBaseView> {
    public D baseView;
    public Context context;

    /**
     * 在子类的构造函数中，设定参数为model，这时候可以presenter调用接口来实现对界面的操作。
     */
    public BasePresenter(Context context, D model) {
        this.context = context;
        this.baseView = model;
    }

    public <T> void post(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<T> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .post(url, map, tClass, new LeoCallBack<T>() {
                    @Override
                    public void onSuccess(T data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }
                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void postList(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<List<T>> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .postList(url, map, tClass, new LeoCallBack<List<T>>() {
                    @Override
                    public void onSuccess(List<T> data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }
                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void postJson(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<T> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .postJson(url, map, tClass, new LeoCallBack<T>() {
                    @Override
                    public void onSuccess(T data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }
                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void postJsonList(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<List<T>> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .postJsonList(url, map, tClass, new LeoCallBack<List<T>>() {
                    @Override
                    public void onSuccess(List<T> data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }
//                        baseView.onSuccess(data);
                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void get(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<T> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .get(url, map, tClass, new LeoCallBack<T>() {
                    @Override
                    public void onSuccess(T data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }
                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void getList(String url, Map<String, Object> map, Class<T> tClass, final OnResponse<List<T>> response) {
        baseView.showLoading();

        LeoHttp.getInstance(context)
                .getList(url, map, tClass, new LeoCallBack<List<T>>() {
                    @Override
                    public void onSuccess(List<T> data) {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccess(data);
                        }

                    }

                    @Override
                    public void onSuccessEmptyOrNull() {
                        baseView.hideLoading();
                        if (response != null) {
                            response.onSuccessEmptyOrNull();
                        }
                    }

                    @Override
                    public void onFailure(int code, String content) {
                        baseView.hideLoading();

                        baseView.showFailedError(code, content);

                        if (response != null) {
                            response.onFailure(code, content);
                        }
                    }
                });
    }

    public <T> void uploadFiles(String url, List<File> files, Map<String, String> description, Class<T> tClass, final LeoProgress<T> onProgress) {
        LeoHttp.getInstance(context)
                .uploadFiles(url, files, description, tClass, onProgress);
    }

    public <T> void uploadFile(String type,String url, File file, Map<String, String> description, Class<T> tClass, final LeoProgress<T> onProgress) {
        LeoHttp.getInstance(context)
                .uploadFile(type,url, file, description, tClass, onProgress);
    }

    public <T> void downloadFile(String url, String filePath, String fileName, Class<T> tClass, final LeoProgress<T> onProgress) {
        LeoHttp.getInstance(context)
                .downLoadFile(url, filePath, fileName, tClass, onProgress);
    }

    /**
     * 默认已经做了处理了，如果还需要其他处理，请创建此接口的实例
     *
     * @param <T>
     */
    public interface OnResponse<T> {
        void onSuccess(T data);

        void onSuccessEmptyOrNull();

        void onFailure(int code, String info);
    }
}
