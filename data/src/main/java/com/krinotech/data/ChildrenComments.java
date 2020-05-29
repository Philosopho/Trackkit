package com.krinotech.data;

import com.google.gson.annotations.SerializedName;

public class ChildrenComments {

    @SerializedName("data")
    private PostComment comment;
    private String kind;

    public ChildrenComments() { }

    public PostComment getComment() {
        return comment;
    }
    public void setComment(PostComment comment) {
        this.comment = comment;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
