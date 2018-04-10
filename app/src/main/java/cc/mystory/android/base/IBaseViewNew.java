package cc.mystory.android.base;

/**
 * View的统一定义
 * <p>
 * Create by Lei on 2016/9/23
 */

public interface IBaseViewNew {
    /**
     * 显示错误信息
     *
     * @param message
     */
    void showFailedError(int code, String message);

//    void onSuccess(T data);

    void onEmptyOrNull();

    void onOtherCode(int code, String info);

    void onProgress(long progress, long total, boolean done);

    void showLoading();

    void hideLoading();
}
