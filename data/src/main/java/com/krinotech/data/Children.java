package com.krinotech.data;

import com.google.gson.annotations.SerializedName;

public class Children {

    private String kind;

    @SerializedName("data")
    private Subreddit subreddit;

    public Children() { }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }
}
