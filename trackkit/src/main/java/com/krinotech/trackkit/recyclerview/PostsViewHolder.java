package com.krinotech.trackkit.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.data.SubredditPost;
import com.krinotech.data.contract.ApiContract;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.PostItemBinding;
import com.krinotech.trackkit.view.CommentsActivity;

import timber.log.Timber;

public class PostsViewHolder extends RecyclerView.ViewHolder {
    private PostItemBinding postItemBinding;

    public PostsViewHolder(@NonNull PostItemBinding postItemBinding) {
        super(postItemBinding.getRoot());

        this.postItemBinding = postItemBinding;
    }

    public void bind(SubredditPost subredditPost) {
        postItemBinding.viewPost.setOnClickListener(viewPostOnClick(subredditPost));
        postItemBinding.viewComments.setOnClickListener(viewCommentsOnClick(subredditPost));
        postItemBinding.setPost(subredditPost);
        postItemBinding.executePendingBindings();
    }

    private View.OnClickListener viewCommentsOnClick(SubredditPost subredditPost) {
        Context context = postItemBinding.numberComments.getContext();
        Resources resources = context.getResources();
        if(subredditPost.getNumberOfComments() <= 0) {
            return v -> Toast.makeText(context, resources.getString(R.string.no_comments), Toast.LENGTH_SHORT)
                    .show();
        }
        else return v -> {
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra(resources.getString(R.string.post_url_extra), subredditPost.getPermanentLinkToPost());
            intent.putExtra(resources.getString(R.string.post_id_extra), subredditPost.getId());
            context.startActivity(intent);
        };
    }

    private View.OnClickListener viewPostOnClick(SubredditPost subredditPost) {
        return v -> {
            Context context = postItemBinding.viewPost.getContext();
            Uri uri = Uri.parse(ApiContract.BASE_REDDIT_URL + subredditPost.getPermanentLinkToPost().substring(1));
            Timber.d(uri.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        };
    }
}
