package cc.mystory.android.net;

/**
 * Create by Lei on 2016/11/10
 */

public interface LeoProgress<T> {

    void onProgress(long progress, long total, boolean done);

    void onSuccess(T data);

    void onSuccessEmptyOrNull();

    void onOtherCode(int code, String errorInfo);
}
