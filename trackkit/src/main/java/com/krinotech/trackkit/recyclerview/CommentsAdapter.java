package com.krinotech.trackkit.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.krinotech.data.PostComment;
import com.krinotech.data.PostTitle;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.CommentItemBinding;
import com.krinotech.trackkit.databinding.ExoplayerLayoutBinding;
import com.krinotech.trackkit.databinding.PostTitleBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private PostTitle postTitle;
    private List<PostComment> postComments;

    private ExoPlayer exoPlayer;
    private ProgressiveMediaSource progressiveMediaSource;

    private final static int TITLE = 1;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TITLE : -TITLE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TITLE:
                PostTitleBinding postTitleBinding =
                        DataBindingUtil.inflate(inflater, R.layout.post_title, parent, false);

//                if(postTitle.isVideo()) {
//                    setUpExoPlayerLayout(postTitleBinding);
//                }
                return new PostContentViewHolders.PostTitleViewHolder(postTitleBinding);
            default:
                CommentItemBinding commentItemBinding =
                        DataBindingUtil.inflate(inflater, R.layout.comment_item, parent, false);
                return new PostContentViewHolders.CommentsViewHolder(commentItemBinding);
        }
    }

    private void setUpExoPlayerLayout(com.krinotech.trackkit.databinding.PostTitleBinding postTitleBinding) {
        postTitleBinding.exoplayerStub.setOnInflateListener(stubInflateListener());

        inflateViewStub(postTitleBinding);
    }

    @NotNull
    private ViewStub.OnInflateListener stubInflateListener() {
        return (stub, inflated) -> {
            ExoplayerLayoutBinding binding = DataBindingUtil.bind(inflated);
            if(binding != null) {
                binding.setPostVideo(postTitle.getPostVideo());
                exoPlayer.addListener(playerListener(binding));
                exoPlayer.setPlayWhenReady(false);
                binding.exoplayerView.setPlayer(exoPlayer);
            }
        };
    }

    private Player.EventListener playerListener(ExoplayerLayoutBinding binding) {
        return new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        Timber.d("onPlayerStateChanged: Buffering video.");
                        binding.pbExoPlayerProgress.setVisibility(VISIBLE);
                        binding.executePendingBindings();
                        break;
                    case Player.STATE_ENDED:
                        Timber.d("onPlayerStateChanged: Video ended.");
                        break;
                    case Player.STATE_IDLE:
                        break;
                    case Player.STATE_READY:
                        Timber.d( "onPlayerStateChanged: Ready to play.");
                        binding.pbExoPlayerProgress.setVisibility(GONE);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void inflateViewStub(PostTitleBinding postTitleBinding) {
        if(!postTitleBinding.exoplayerStub.isInflated()) {
            postTitleBinding.exoplayerStub.getViewStub().inflate();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == TITLE){
            ((PostContentViewHolders.PostTitleViewHolder) holder)
                    .bind(postTitle);
        }
        else {
            PostComment postComment = postComments.get(position - 1);
            ((PostContentViewHolders.CommentsViewHolder) holder)
                    .bind(postComment);
        }
    }

    @Override
    public int getItemCount() {
        if(postComments != null) {
            return postComments.size() + 1;
        }
        return 0;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
        notifyDataSetChanged();
    }

    public void setPostTitle(PostTitle postTitle) {
        this.postTitle = postTitle;
    }

    public void setExoPlayer(ExoPlayer exoPlayer) {
        this.exoPlayer = exoPlayer;
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

}
