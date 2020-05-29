package com.krinotech.trackkit.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.krinotech.data.DataPresenter;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.ActivityViewPostBinding;
import com.krinotech.trackkit.recyclerview.PostsAdapter;
import com.krinotech.trackkit.viewmodel.PostsViewModel;
import com.krinotech.trackkit.viewmodel.PostsViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ViewPostsActivity extends AppCompatActivity {
    @Inject
    PostsViewModelFactory postsViewModelFactory;

    private PostsViewModel postsViewModel;
    private PostsAdapter postsAdapter;

    ActivityViewPostBinding activityViewPostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityViewPostBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_post);

        postsViewModel = new ViewModelProvider(this, postsViewModelFactory).get(PostsViewModel.class);

        setUpAdapter();

        String redditUrl = getIntent().getStringExtra(getString(R.string.reddit_url_extra));
        String subredditId = getIntent().getStringExtra(getString(R.string.subreddit_id_extra));
        int end = redditUrl.length() - 1;
        redditUrl = redditUrl.substring(1, end);

        showProgress();
        postsViewModel.getSubredditPosts(redditUrl, subredditId).observe(this, subredditPosts -> {
            if(DataPresenter.isInvalid(subredditPosts)) {
                showErrorText();
                Snackbar.make(activityViewPostBinding.tvErrorText, getString(R.string.no_posts), Snackbar.LENGTH_LONG)
                        .show();
            }
            else if(DataPresenter.isAllFinishedLoading()) {
                postsAdapter.setSubredditPosts(subredditPosts);
                showPosts();
            }
        });
    }

    private void setUpAdapter() {
        postsAdapter = new PostsAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this, RecyclerView.HORIZONTAL, false);

        activityViewPostBinding.rvViewPosts.setLayoutManager(linearLayoutManager);

        activityViewPostBinding.rvViewPosts.setAdapter(postsAdapter);
    }

    private void showProgress() {
        activityViewPostBinding.pbViewPosts.setVisibility(View.VISIBLE);
        activityViewPostBinding.rvViewPosts.setVisibility(View.GONE);
    }

    private void showErrorText() {
        activityViewPostBinding.tvErrorText.setVisibility(View.VISIBLE);
        activityViewPostBinding.pbViewPosts.setVisibility(View.GONE);
        activityViewPostBinding.rvViewPosts.setVisibility(View.GONE);
    }

    private void showPosts() {
        activityViewPostBinding.tvErrorText.setVisibility(View.GONE);
        activityViewPostBinding.pbViewPosts.setVisibility(View.GONE);
        activityViewPostBinding.rvViewPosts.setVisibility(View.VISIBLE);
    }




}
