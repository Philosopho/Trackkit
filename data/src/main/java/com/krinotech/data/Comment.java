package com.krinotech.data;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("permalink")
    private String permaLink;

    private String url;
    private String author;
    private int ups;
    private int downs;
    private String body;

    Comment() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public String getUpsString() {
        return "Ups: " + ups;
    }

    public String getDownsString() {
        return "Downs: " + downs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }
}
