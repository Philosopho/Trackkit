package com.krinotech.data;

import com.google.gson.annotations.SerializedName;

class ChildrenPosts {
    @SerializedName("data")
    private SubredditPost subredditPost;

    private String kind;

    public ChildrenPosts() { }

    public SubredditPost getSubredditPost() {
        return subredditPost;
    }
    public void setSubredditPost(SubredditPost subredditPost) {
        this.subredditPost = subredditPost;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
