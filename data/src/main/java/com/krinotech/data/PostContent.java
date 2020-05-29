package com.krinotech.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PostContent {

    public PostContent() {}

    @Embedded
    private PostTitle postTitle;

    @Relation(parentColumn = "subredditPostId", entityColumn = "postTitleId", entity = PostComment.class)
    private List<PostComment> postComments;

    public PostTitle getPostTitle() {
        return postTitle;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public void setPostTitle(PostTitle postTitle) {
        this.postTitle = postTitle;
    }
}
