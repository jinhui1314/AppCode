package cc.mystory.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by Administrator on 2016/8/19.
 */

public class UserBean implements Serializable,Parcelable {


    private int userId;

    private String uniqueId;

    private String username;

    private String nickname;

    private int sex;

    private String birthday;

    private String signature;

    private String description;

    private String cellphone;

    private long createTime;

    private String accessToken;

    private String headerUrl;

    private int deleted;

    private String address;

    private String settings;

    private String thumbEditor;

    private String inviterUniqueId;

    private String location;

    private String supportQualification;

    private long time;

    private int fansNumber;

    private int followNumber;

    private String loginType;

    private long timestamp;


    public void setTime(long time) {
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    protected UserBean(Parcel in) {
        userId = in.readInt();
        uniqueId = in.readString();
        username = in.readString();
        nickname = in.readString();
        sex = in.readInt();
        birthday = in.readString();
        signature = in.readString();
        description = in.readString();
        cellphone = in.readString();
        createTime = in.readLong();
        accessToken = in.readString();
        headerUrl = in.readString();
        deleted = in.readInt();
        address = in.readString();
        settings = in.readString();
        thumbEditor = in.readString();
        inviterUniqueId = in.readString();
        location = in.readString();
        supportQualification = in.readString();
        time = in.readLong();
        fansNumber = in.readInt();
        followNumber = in.readInt();
        loginType = in.readString();
        timestamp=in.readLong();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setUniqueId(String uniqueId){
        this.uniqueId = uniqueId;
    }
    public String getUniqueId(){
        return this.uniqueId;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public void setSex(int sex){
        this.sex = sex;
    }
    public int getSex(){
        return this.sex;
    }
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }
    public String getBirthday(){
        return this.birthday;
    }
    public void setSignature(String signature){
        this.signature = signature;
    }
    public String getSignature(){
        return this.signature;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setCellphone(String cellphone){
        this.cellphone = cellphone;
    }
    public String getCellphone(){
        return this.cellphone;
    }
    public void setCreateTime(long createTime){
        this.createTime = createTime;
    }
    public long getCreateTime(){
        return this.createTime;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
    public String getAccessToken(){
        return this.accessToken;
    }
    public void setHeaderUrl(String headerUrl){
        this.headerUrl = headerUrl;
    }
    public String getHeaderUrl(){
        return this.headerUrl;
    }
    public void setDeleted(int deleted){
        this.deleted = deleted;
    }
    public int getDeleted(){
        return this.deleted;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setSettings(String settings){
        this.settings = settings;
    }
    public String getSettings(){
        return this.settings;
    }
    public void setThumbEditor(String thumbEditor){
        this.thumbEditor = thumbEditor;
    }
    public String getThumbEditor(){
        return this.thumbEditor;
    }
    public void setInviterUniqueId(String inviterUniqueId){
        this.inviterUniqueId = inviterUniqueId;
    }
    public String getInviterUniqueId(){
        return this.inviterUniqueId;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getLocation(){
        return this.location;
    }
    public void setSupportQualification(String supportQualification){
        this.supportQualification = supportQualification;
    }
    public String getSupportQualification(){
        return this.supportQualification;
    }
    public void setTime(Long time){
        this.time = time;
    }
    public Long getTime(){
        return this.time;
    }
    public void setFansNumber(int fansNumber){
        this.fansNumber = fansNumber;
    }
    public int getFansNumber(){
        return this.fansNumber;
    }
    public void setFollowNumber(int followNumber){
        this.followNumber = followNumber;
    }
    public int getFollowNumber(){
        return this.followNumber;
    }
    public void setLoginType(String loginType){
        this.loginType = loginType;
    }
    public String getLoginType(){
        return this.loginType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(uniqueId);
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeInt(sex);
        dest.writeString(birthday);
        dest.writeString(signature);
        dest.writeString(description);
        dest.writeString(cellphone);
        dest.writeLong(createTime);
        dest.writeString(accessToken);
        dest.writeString(headerUrl);
        dest.writeInt(deleted);
        dest.writeString(address);
        dest.writeString(settings);
        dest.writeString(thumbEditor);
        dest.writeString(inviterUniqueId);
        dest.writeString(location);
        dest.writeString(supportQualification);
        dest.writeLong(time);
        dest.writeInt(fansNumber);
        dest.writeInt(followNumber);
        dest.writeString(loginType);
    }
}

