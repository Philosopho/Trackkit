package com.krinotech.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;

@Entity(tableName = "subreddit")
public class Subreddit {
    public static final String DISPLAY_NAME = "display_name";
    public static final String COMMUNITY_ICON = "community_icon";
    public static final String USER_IS_SUBSCRIBER = "user_is_subscriber";

    @NonNull
    @PrimaryKey
    private String id;

    private String title;
    private String url;
    private long subscribers;
    private boolean over18;

    private String description;
    private boolean isTracking;
    private String type;

    @ColumnInfo(defaultValue = DISPLAY_NAME)
    @SerializedName(DISPLAY_NAME)
    private String displayName;
    @ColumnInfo(defaultValue = COMMUNITY_ICON)
    @SerializedName(COMMUNITY_ICON)
    private String headerImage;
    @ColumnInfo(defaultValue = USER_IS_SUBSCRIBER)
    @SerializedName(USER_IS_SUBSCRIBER)
    private boolean userIsSubscriber;

    @Ignore
    private boolean descriptionLimited = true;

    @Ignore
    public Subreddit(
            String id, String title, String url,
            String subredditType, String displayName,
            long subscribers, boolean userIsSubscriber,
            String headerImage, boolean isTracking, String description) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.type = subredditType;
        this.displayName = displayName;
        this.headerImage = headerImage;
        this.subscribers = subscribers;
        this.userIsSubscriber = userIsSubscriber;
        this.isTracking = isTracking;
        this.description = description;
    }

    public Subreddit(
            String id, String title, String url,
            String subredditType, String displayName,
            long subscribers, boolean userIsSubscriber,
            String headerImage, String description) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.type = subredditType;
        this.displayName = displayName;
        this.headerImage = headerImage;
        this.subscribers = subscribers;
        this.userIsSubscriber = userIsSubscriber;
        this.description = description;
    }

    public Subreddit() {

    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String subredditType) {
        this.type = subredditType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSubscribersFormatted() {
        return NumberFormat.getInstance().format(subscribers) + " subscribers";
    }

    public long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(long subscribers) {
        this.subscribers = subscribers;
    }

    public boolean getUserIsSubscriber() {
        return userIsSubscriber;
    }

    public void setUserIsSubscriber(boolean userIsSubscriber) {
        this.userIsSubscriber = userIsSubscriber;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setIsTracking(boolean isTracking) {
        this.isTracking = isTracking;
    }

    public boolean isOver18() {
        return over18;
    }

    public void setOver18(boolean over18) {
        this.over18 = over18;
    }

    public boolean isDescriptionLimited() {
        return descriptionLimited;
    }

    public void setDescriptionLimited(boolean descriptionExpanded) {
        this.descriptionLimited = descriptionExpanded;
    }
}
