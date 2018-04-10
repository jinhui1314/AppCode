package cc.mystory.android.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/19.
 */

public class SelfBean implements Serializable {
    private int userId;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "SelfBean{" +
                "userId = " + userId +
                ", accessToken = " + accessToken;
    }
}
