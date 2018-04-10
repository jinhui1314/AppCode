package cc.mystory.android.model;

/**
 * Created by XK on 16/4/11.
 */
public class ThirdLoginData {

    private String openId;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "ThirdLoginData{" +
                "accessToken='" + accessToken + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
