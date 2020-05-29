package com.krinotech.trackkit.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.data.DataPresenter;
import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.FragmentSubredditsBinding;
import com.krinotech.trackkit.recyclerview.SubredditsAdapter;
import com.krinotech.trackkit.view.ViewPostsActivity;
import com.krinotech.trackkit.viewmodel.SubredditViewModelFactory;
import com.krinotech.trackkit.viewmodel.SubredditsViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TrackingFragment extends Fragment implements SubredditsAdapter.ViewOnClicks {
    @Inject
    SubredditViewModelFactory subredditViewModelFactory;

    @Inject
    TrackkitPreferences trackkitPreferences;

    private FragmentSubredditsBinding fragmentSubredditsBinding;

    private SubredditsViewModel subredditsViewModel;

    private SubredditsAdapter subredditsAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentSubredditsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_subreddits, container, false);
        setUpAdapter();
        subredditsViewModel = new ViewModelProvider(this, subredditViewModelFactory).get(SubredditsViewModel.class);

        observeTrackingSubreddits();
        return fragmentSubredditsBinding.getRoot();
    }

    private void observeTrackingSubreddits() {
        showProgress();
        subredditsViewModel
                .getTrackingSubreddits()
                .observe(this.getViewLifecycleOwner(), this::setSubreddits);
    }

    private void setSubreddits(List<Subreddit> subreddits) {
        if(DataPresenter.isInvalid(subreddits)) {
            showNoTrackingSubreddits();
        }
        else if(DataPresenter.isAllFinishedLoading()) {
            subredditsAdapter.setSubreddits(subreddits);
            showTrackingSubreddits();
        }
    }

    private void showNoTrackingSubreddits() {
        fragmentSubredditsBinding.tvNoSubreddits.setText(R.string.no_tracking_subreddits);
        fragmentSubredditsBinding.tvNoSubreddits.setVisibility(View.VISIBLE);
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.rvFragmentSubreddits.setVisibility(View.GONE);
    }

    private void showTrackingSubreddits() {
        fragmentSubredditsBinding.tvNoSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.rvFragmentSubreddits.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.VISIBLE);
    }

    private void setUpAdapter() {
        subredditsAdapter = new SubredditsAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), RecyclerView.VERTICAL, false);

        fragmentSubredditsBinding.rvFragmentSubreddits.setLayoutManager(linearLayoutManager);

        fragmentSubredditsBinding.rvFragmentSubreddits.setAdapter(subredditsAdapter);
    }

    @Override
    public boolean checkboxClick(Subreddit subreddit) {
        return subredditsViewModel.addOrRemoveSubredditFromTracking(subreddit);
    }


    @Override
    public int viewPostsVisibility() {
        return View.VISIBLE;
    }

    @Override
    public View.OnClickListener viewPosts(Subreddit subreddit) {
        return v -> {
            Intent intent = new Intent(TrackingFragment.this.getContext(), ViewPostsActivity.class);
            intent.putExtra(getString(R.string.reddit_url_extra), subreddit.getUrl());
            intent.putExtra(getString(R.string.subreddit_id_extra), subreddit.getId());
            startActivity(intent);
        };
    }
}
