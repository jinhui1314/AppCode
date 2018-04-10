package cc.mystory.android.net;

/**
 * Create by Lei on 2016/11/5
 */

public interface LeoCallBack<T> {
    void onSuccess(T data);

    void onSuccessEmptyOrNull();

    void onFailure(int code, String content);
}
