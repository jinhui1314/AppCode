package cc.mystory.android.model;

import java.io.Serializable;

/**
 * Created by jinhui on 2018/3/27.
 */

public class LeTouModle implements Serializable{
    private String title;
    private String url;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
