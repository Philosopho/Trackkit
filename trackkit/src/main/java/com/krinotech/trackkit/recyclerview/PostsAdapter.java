package com.krinotech.trackkit.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.data.SubredditPost;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.PostItemBinding;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {
    private List<SubredditPost> subredditPosts;

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        PostItemBinding postItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.post_item, parent, false);
        return new PostsViewHolder(postItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        SubredditPost subredditPost = subredditPosts.get(position);
        holder.bind(subredditPost);
    }

    @Override
    public int getItemCount() {
        if(subredditPosts != null) {
            return subredditPosts.size();
        }
        return 0;
    }

    public void setSubredditPosts(List<SubredditPost> subredditPosts) {
        this.subredditPosts = subredditPosts;
        notifyDataSetChanged();
    }
}
