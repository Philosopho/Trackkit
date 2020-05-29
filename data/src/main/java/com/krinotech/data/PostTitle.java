package com.krinotech.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "postTitle")
public class PostTitle extends Comment {

    @NonNull
    public String getSubredditPostId() {
        return subredditPostId;
    }

    public void setSubredditPostId(@NonNull String subredditPostId) {
        this.subredditPostId = subredditPostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @PrimaryKey
    private String subredditPostId;
    private String title;
    @SerializedName("selftext")
    private String selfText;
    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(int thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(int thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public int getUpvoteRatio() {
        return upvoteRatio;
    }

    public void setUpvoteRatio(int upvoteRatio) {
        this.upvoteRatio = upvoteRatio;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public int getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(int createdUtc) {
        this.createdUtc = createdUtc;
    }

    @SerializedName("is_video")
    private boolean isVideo;
    @SerializedName("thumbnail_height")
    private int thumbnailHeight;
    @SerializedName("thumbnail_width")
    private int thumbnailWidth;
    @SerializedName("upvote_ratio")
    private int upvoteRatio;
    @SerializedName("num_comments")
    private int numberOfComments;
    @SerializedName("created_utc")
    private int createdUtc;
    @Embedded
    private PostVideo postVideo;

    public PostVideo getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(PostVideo postVideo) {
        this.postVideo = postVideo;
    }

    public String getSelfText() {
        return selfText;
    }

    public void setSelfText(String selfText) {
        this.selfText = selfText;
    }
}
