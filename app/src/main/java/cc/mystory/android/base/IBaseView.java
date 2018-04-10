package cc.mystory.android.base;

public interface IBaseView {
    void showFailedError(int code, String message);

    void onEmptyOrNull();

    void onProgress(long progress, long total, boolean done);

    void showLoading();

    void hideLoading();
}