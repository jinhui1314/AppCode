package cc.mystory.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiang on 2017/6/27.
 */

public class StoryInfoBean implements Parcelable {
    private String uniqueId;
    private String title;
    private String content;
    private Author author;
    private int browseCount;
    private int thumbCount;
    private int commentCount;
    private int thumbByMe;
    private int followedByMe;
    private int time;
    private String status;
    private String brief;
    private String image;
    private int collectByMe;

    protected StoryInfoBean(Parcel in) {
        uniqueId = in.readString();
        title = in.readString();
        content = in.readString();
        browseCount = in.readInt();
        thumbCount = in.readInt();
        commentCount = in.readInt();
        thumbByMe = in.readInt();
        followedByMe = in.readInt();
        time = in.readInt();
        status = in.readString();
        brief = in.readString();
        image = in.readString();
        collectByMe = in.readInt();
    }

    public static final Creator<StoryInfoBean> CREATOR = new Creator<StoryInfoBean>() {
        @Override
        public StoryInfoBean createFromParcel(Parcel in) {
            return new StoryInfoBean(in);
        }

        @Override
        public StoryInfoBean[] newArray(int size) {
            return new StoryInfoBean[size];
        }
    };

    public int getCollectByMe() {
        return collectByMe;
    }

    public void setCollectByMe(int collectByMe) {
        this.collectByMe = collectByMe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getIcon() {
        return image;
    }

    public void setIcon(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setThumbCount(int thumbCount) {
        this.thumbCount = thumbCount;
    }

    public int getThumbCount() {
        return thumbCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setThumbByMe(int thumbByMe) {
        this.thumbByMe = thumbByMe;
    }

    public int getThumbByMe() {
        return thumbByMe;
    }

    public void setFollowedByMe(int followedByMe) {
        this.followedByMe = followedByMe;
    }

    public int getFollowedByMe() {
        return followedByMe;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uniqueId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(browseCount);
        dest.writeInt(thumbCount);
        dest.writeInt(commentCount);
        dest.writeInt(thumbByMe);
        dest.writeInt(followedByMe);
        dest.writeInt(time);
        dest.writeString(status);
        dest.writeInt(collectByMe);
    }

    public class Author {

        private String userUniqueId;
        private String headerUrl;
        private String nickname;

        public void setUserUniqueId(String userUniqueId) {
            this.userUniqueId = userUniqueId;
        }

        public String getUserUniqueId() {
            return userUniqueId;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

    }
}
