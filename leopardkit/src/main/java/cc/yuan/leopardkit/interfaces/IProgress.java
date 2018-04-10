package cc.yuan.leopardkit.interfaces;

/**
 * Created by Yuan on 2016/8/25.
 * Detail 进度监听 提供给下载与上传进度，依赖主线程
 */
public interface IProgress {

    void onProgress(long progress, long total, boolean done);

    void onSuccess(String content);
}
