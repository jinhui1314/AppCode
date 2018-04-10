package cc.mystory.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiang on 2017/6/15.
 */

public class StoryBean implements Parcelable {
    private String uniqueId;
    private String title;
    private String brief;
    private String image;
    private Author author;
    private int browseCount;
    private int thumbCount;
    private int commentCount;
    private String thumbByMe;
    private long time;
    private String thumbUserNewUniqueId;
    private String thumbUserNewNickname;

    public StoryBean() {
    }

    protected StoryBean(Parcel in) {
        uniqueId = in.readString();
        title = in.readString();
        brief = in.readString();
        image = in.readString();
        browseCount = in.readInt();
        thumbCount = in.readInt();
        commentCount = in.readInt();
        thumbByMe = in.readString();
        time = in.readLong();
        thumbUserNewUniqueId = in.readString();
        thumbUserNewNickname = in.readString();
    }

    public static final Creator<StoryBean> CREATOR = new Creator<StoryBean>() {
        @Override
        public StoryBean createFromParcel(Parcel in) {
            return new StoryBean(in);
        }

        @Override
        public StoryBean[] newArray(int size) {
            return new StoryBean[size];
        }
    };

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

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getBrief() {
        return brief;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
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

    public void setThumbByMe(String thumbByMe) {
        this.thumbByMe = thumbByMe;
    }

    public String getThumbByMe() {
        return thumbByMe;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setThumbUserNewUniqueId(String thumbUserNewUniqueId) {
        this.thumbUserNewUniqueId = thumbUserNewUniqueId;
    }

    public String getThumbUserNewUniqueId() {
        return thumbUserNewUniqueId;
    }

    public void setThumbUserNewNickname(String thumbUserNewNickname) {
        this.thumbUserNewNickname = thumbUserNewNickname;
    }

    public String getThumbUserNewNickname() {
        return thumbUserNewNickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uniqueId);
        parcel.writeString(title);
        parcel.writeString(brief);
        parcel.writeString(image);
        parcel.writeInt(browseCount);
        parcel.writeInt(thumbCount);
        parcel.writeInt(commentCount);
        parcel.writeString(thumbByMe);
        parcel.writeLong(time);
        parcel.writeString(thumbUserNewUniqueId);
        parcel.writeString(thumbUserNewNickname);
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
