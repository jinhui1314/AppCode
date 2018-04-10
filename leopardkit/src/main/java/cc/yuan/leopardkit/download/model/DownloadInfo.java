package cc.yuan.leopardkit.download.model;

import cc.yuan.leopardkit.download.DownLoadManager;
import cc.yuan.leopardkit.download.task.DownLoadSubscriber;
import cc.yuan.leopardkit.download.task.DownLoadTask;
import cc.yuan.leopardkit.models.FileLoadInfo;

/**
 * Created by Yuan on 2016/8/29.
 * Detail 下载信息实体类
 */
public class DownloadInfo extends FileLoadInfo {

    private DownLoadTask downLoadTask;

    private DownLoadSubscriber subscriber;


    public DownloadInfo() {
        this.setState(DownLoadManager.STATE_WAITING);
        this.setType(0);
    }

    public DownLoadSubscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(DownLoadSubscriber subscriber) {
        this.subscriber = subscriber;
    }

    public DownLoadTask getDownLoadTask() {
        return downLoadTask;
    }

    public void setDownLoadTask(DownLoadTask downLoadTask) {
        this.downLoadTask = downLoadTask;
    }

}
