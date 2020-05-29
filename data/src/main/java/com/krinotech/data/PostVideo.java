package com.krinotech.data;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class PostVideo {
    public PostVideo() {}

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

    public String getScrubberMediaUrl() {
        return scrubberMediaUrl;
    }

    public void setScrubberMediaUrl(String scrubberMediaUrl) {
        this.scrubberMediaUrl = scrubberMediaUrl;
    }

    public String getDashUrl() {
        return dashUrl;
    }

    public void setDashUrl(String dashUrl) {
        this.dashUrl = dashUrl;
    }

    public String getIsGif() {
        return isGif;
    }

    public void setIsGif(String isGif) {
        this.isGif = isGif;
    }

    public String getTranscodingStatus() {
        return transcodingStatus;
    }

    public void setTranscodingStatus(String transcodingStatus) {
        this.transcodingStatus = transcodingStatus;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;
    @SerializedName("fallback_url")
    private String fallbackUrl;
    @SerializedName("scrubber_media_url")
    private String scrubberMediaUrl;
    @SerializedName("dash_url")
    private String dashUrl;
    @SerializedName("is_gif")
    private String isGif;
    @SerializedName("transcoding_status")
    private String transcodingStatus;
    private int width;

    private int height;
    private String duration;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
