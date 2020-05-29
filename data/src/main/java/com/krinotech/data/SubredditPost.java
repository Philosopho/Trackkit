package com.krinotech.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "subredditPost")
public class SubredditPost {
    @NonNull
    @PrimaryKey
    private String id;

    @SerializedName("subreddit_name_prefixed")
    private String subredditName;
    @SerializedName("permalink")
    private String permanentLinkToPost;
    @SerializedName("num_comments")
    private int numberOfComments;
    @SerializedName("author_fullname")
    private String postedBy;

    @SerializedName("is_video")
    private boolean isVideo;
    @SerializedName("url")
    private String externalThumbnailLink;

    private String title;
    private String subredditId;

    private boolean over18;

    private String thumbnail;

    public SubredditPost() { }

    @NonNull
    public String getId() {
        return id;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public String getPermanentLinkToPost() {
        return permanentLinkToPost;
    }

    public void setPermanentLinkToPost(String permanentLinkToPost) {
        this.permanentLinkToPost = permanentLinkToPost;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public String getNumberOfCommentsString() {
        return numberOfComments + " comments";
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOver18() {
        return over18;
    }

    public void setOver18(boolean over18) {
        this.over18 = over18;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getExternalThumbnailLink() {
        return externalThumbnailLink;
    }

    public void setExternalThumbnailLink(String externalThumbnailLink) {
        this.externalThumbnailLink = externalThumbnailLink;
    }
}
