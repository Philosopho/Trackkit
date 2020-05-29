package com.krinotech.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "postComment")
public class PostComment extends Comment {

    public PostComment() { }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    @PrimaryKey
    private String id;

    @ForeignKey(
            entity = PostTitle.class,
            parentColumns = {"subredditPostId"},
            childColumns = {"postTitleId"},
            onDelete = CASCADE)
    private String postTitleId;

    public String getPostTitleId() {
        return postTitleId;
    }

    public void setPostTitleId(String postTitleId) {
        this.postTitleId = postTitleId;
    }
}
