package com.krinotech.trackkit.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.material.snackbar.Snackbar;
import com.krinotech.data.DataPresenter;
import com.krinotech.data.PostTitle;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.ActivityCommentsBinding;
import com.krinotech.trackkit.recyclerview.CommentsAdapter;
import com.krinotech.trackkit.viewmodel.CommentsViewModel;
import com.krinotech.trackkit.viewmodel.CommentsViewModelFactory;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjection;

import static com.krinotech.trackkit.NetworkConnectionHelper.isConnected;

public class CommentsActivity extends AppCompatActivity {

    @Inject
    Lazy<ExoPlayer> exoPlayerProvider;
    @Inject
    Lazy<ProgressiveMediaSource.Factory> progressiveMediaSourceFactory;
    @Inject
    CommentsViewModelFactory commentsViewModelFactory;

    private CommentsViewModel commentViewModel;
    private CommentsAdapter commentsAdapter;
    private ExoPlayer exoPlayer;

    ActivityCommentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        if(!isConnected(this)) {
            binding.errorTextComments.setText(getString(R.string.network_error));
            binding.errorTextComments.setVisibility(View.VISIBLE);
            binding.pbIncluded.pbComments.setVisibility(View.GONE);
        }
        else {
            String postUrl = getIntent().getStringExtra(getString(R.string.post_url_extra));
            String postId = getIntent().getStringExtra(getString(R.string.post_id_extra));
            if(postUrl != null) {
                postUrl = postUrl.substring(1);
            }

            commentViewModel = new ViewModelProvider(this, commentsViewModelFactory).get(CommentsViewModel.class);

            setUpAdapter();

            showProgress();
            commentViewModel.getSubredditPosts(postUrl, postId).observe(this, postContent -> {
                if(DataPresenter.isInvalid(postContent)) {
                    showErrorText();
                    Snackbar.make(binding.errorTextComments, getString(R.string.posts_retrieval_error), Snackbar.LENGTH_LONG).show();
                }
                else if(DataPresenter.isAllFinishedLoading()) {
                    // TODO: Bind PostTitle content to the view above the RecyclerView
                    commentsAdapter.setPostComments(postContent.getPostComments());
                    PostTitle postTitle = postContent.getPostTitle();

//                    checkVideo(postTitle);

                    showComments();
                    commentsAdapter.setPostTitle(postTitle);

                }
            });
        }
    }

    private void checkVideo(PostTitle postTitle) {
        if(postTitle.isVideo()) {
            Uri videoUrl = Uri.parse(postTitle.getPostVideo().getScrubberMediaUrl());
            ProgressiveMediaSource progressiveMediaSource =
                    progressiveMediaSourceFactory.get().createMediaSource(videoUrl);
            exoPlayer = exoPlayerProvider.get();
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.prepare(progressiveMediaSource);
            commentsAdapter.setExoPlayer(exoPlayer);
        }
    }

    @Override
    protected void onStop() {
        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
        super.onStop();
    }

    private void setUpAdapter() {
        commentsAdapter = new CommentsAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false);

        binding.rvComments.setLayoutManager(linearLayoutManager);

        binding.rvComments.setAdapter(commentsAdapter);
    }

    private void showProgress() {
        binding.pbIncluded.pbComments.setVisibility(View.VISIBLE);
        binding.rvComments.setVisibility(View.GONE);
        binding.errorTextComments.setVisibility(View.GONE);
    }

    private void showErrorText() {
        binding.errorTextComments.setVisibility(View.VISIBLE);
        binding.pbIncluded.pbComments.setVisibility(View.GONE);
        binding.rvComments.setVisibility(View.GONE);
    }

    private void showComments() {
        binding.errorTextComments.setVisibility(View.GONE);
        binding.pbIncluded.pbComments.setVisibility(View.GONE);
        binding.rvComments.setVisibility(View.VISIBLE);
    }

}
