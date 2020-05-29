package com.krinotech.trackkit.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.data.Subreddit;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.AdLayoutBinding;
import com.krinotech.trackkit.databinding.SubredditListItemBinding;

import java.util.List;

import timber.log.Timber;

public class SubredditsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Subreddit> subreddits;
    private ViewOnClicks viewOnClicks;
    private final int AD = 1;

    public interface ViewOnClicks {
        boolean checkboxClick(Subreddit subreddit);
        int viewPostsVisibility();
        View.OnClickListener viewPosts(Subreddit subreddit);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? AD : -AD;
    }

    public SubredditsAdapter(ViewOnClicks viewOnClicks) {
        this.viewOnClicks = viewOnClicks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case AD:
                AdLayoutBinding adLayoutBinding =
                        DataBindingUtil.inflate(layoutInflater, R.layout.ad_layout, parent, false);
                return new AdViewHolder(adLayoutBinding);
            default:
                SubredditListItemBinding subredditListItemBinding = DataBindingUtil
                        .inflate(layoutInflater, R.layout.subreddit_list_item, parent, false);

                return new SubredditsViewHolder(subredditListItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position != 0) {
            Subreddit subreddit = subreddits.get(position - 1);
            Timber.d(String.valueOf(position));
            ((SubredditsViewHolder)holder).bind(subreddit, viewOnClicks);
        }
    }

    @Override
    public int getItemCount() {
        if(subreddits == null) {
            return 0;
        }
        return subreddits.size() + 1;
    }

    public void setSubreddits(List<Subreddit> subreddits) {
        this.subreddits = subreddits;
        notifyDataSetChanged();
    }
}
