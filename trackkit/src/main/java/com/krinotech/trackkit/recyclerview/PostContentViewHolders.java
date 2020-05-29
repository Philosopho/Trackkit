package com.krinotech.trackkit.recyclerview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.data.PostComment;
import com.krinotech.data.PostTitle;
import com.krinotech.trackkit.databinding.CommentItemBinding;
import com.krinotech.trackkit.databinding.PostTitleBinding;

public class PostContentViewHolders {

    public static class PostTitleViewHolder extends RecyclerView.ViewHolder {
        private PostTitleBinding postTitleViewHolder;

        PostTitleViewHolder(@NonNull PostTitleBinding postTitleBinding) {
            super(postTitleBinding.getRoot());

            this.postTitleViewHolder = postTitleBinding;
        }

        public void bind(PostTitle postTitle) {
            postTitleViewHolder.setPostTitle(postTitle);
            postTitleViewHolder.executePendingBindings();
        }
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private CommentItemBinding commentItemBinding;

        CommentsViewHolder(@NonNull CommentItemBinding commentItemBinding) {
            super(commentItemBinding.getRoot());

            this.commentItemBinding = commentItemBinding;
        }

        public void bind(PostComment postComment) {
            commentItemBinding.setComment(postComment);
            commentItemBinding.executePendingBindings();
        }
    }
}
