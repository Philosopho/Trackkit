package com.krinotech.trackkit.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.krinotech.data.DataPresenter;
import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.MenuItemHelper;
import com.krinotech.domain.SubredditType;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.databinding.FragmentSubredditsBinding;
import com.krinotech.trackkit.recyclerview.SubredditsAdapter;
import com.krinotech.trackkit.viewmodel.SubredditViewModelFactory;
import com.krinotech.trackkit.viewmodel.SubredditsViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.krinotech.trackkit.NetworkConnectionHelper.isConnected;


public class SubredditsFragment extends Fragment implements SubredditsAdapter.ViewOnClicks {
    @Inject SubredditViewModelFactory subredditViewModelFactory;

    @Inject
    TrackkitPreferences trackkitPreferences;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private SubredditsViewModel subredditsViewModel;
    private SubredditsAdapter subredditsAdapter;

    private FragmentSubredditsBinding fragmentSubredditsBinding;
    private MenuItem menuDefault;
    private MenuItem menuPopular;
    private MenuItem menuNew;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        fragmentSubredditsBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_subreddits, container, false);

        setUpAdapter();

        subredditsViewModel = new ViewModelProvider(this, subredditViewModelFactory).get(SubredditsViewModel.class);

        observeSubreddits();

        return fragmentSubredditsBinding.getRoot();
    }

    private void observeSubreddits() {
        SubredditType subredditType = trackkitPreferences.getCurrentSelected();
        MenuItemHelper.reset();
        switch (subredditType) {
            case NEW:
                checkNetwork(SubredditType.NEW,
                        MenuItemHelper.isNewOff(),
                        getString(R.string.new_selected));
                return;
            case DEFAULT:
                checkNetwork(SubredditType.DEFAULT,
                        MenuItemHelper.isDefaultOff(),
                        getString(R.string.default_selected));
                return;
            case POPULAR:
                checkNetwork(SubredditType.POPULAR,
                        MenuItemHelper.isPopularOff(),
                        getString(R.string.popular_selected));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.subreddit_sort, menu);
        menuPopular = menu.getItem(0);
        menuNew = menu.getItem(1);
        menuDefault = menu.getItem(2);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.mi_popular:
                checkNetwork(SubredditType.POPULAR,
                        MenuItemHelper.isPopularOff(),
                        getString(R.string.popular_selected));
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuDefault.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                menuNew.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
            case R.id.mi_new:
                checkNetwork(SubredditType.NEW,
                        MenuItemHelper.isNewOff(),
                        getString(R.string.new_selected));
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuPopular.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                menuDefault.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
            case R.id.mi_default:
                checkNetwork(SubredditType.DEFAULT,
                        MenuItemHelper.isDefaultOff(),
                        getString(R.string.default_selected));
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuNew.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                menuPopular.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
        }
        return true;
    }

    @Override
    public boolean checkboxClick(Subreddit subreddit) {
        subredditsViewModel.removeAllObservers(this.getViewLifecycleOwner());
        return subredditsViewModel.addOrRemoveSubredditFromTracking(subreddit);
    }

    private void checkNetwork(SubredditType subredditType, boolean isOff, String toastMessage) {
        if(isConnected(getContext())){

            if(isOff){
                getSubreddits(subredditType);
                sendAnalytics(subredditType);
            }
            else {
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }

        }
        else {
            showToastNetworkError();
        }
    }

    private void sendAnalytics(SubredditType subredditType) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, subredditType.name());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, getString(R.string.analytics_btn));
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void setUpAdapter() {
        subredditsAdapter = new SubredditsAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), RecyclerView.VERTICAL, false);

        fragmentSubredditsBinding.rvFragmentSubreddits.setLayoutManager(linearLayoutManager);

        fragmentSubredditsBinding.rvFragmentSubreddits.setAdapter(subredditsAdapter);
    }

    private void getSubreddits(SubredditType subredditType) {
        showProgress();
        subredditsViewModel
                .getSubreddits(subredditType)
                .observe(this.getViewLifecycleOwner(), this::setSubreddits);
        trackkitPreferences.saveSelected(subredditType);
    }

    private void setSubreddits(List<Subreddit> subreddits) {
        if(DataPresenter.isInvalid(subreddits)) {
            showErrorText();
            MenuItemHelper.reset();
        }
        if(DataPresenter.isAllFinishedLoading()) {
            subredditsAdapter.setSubreddits(subreddits);
            showSubreddits();
        }
    }

    private void showToastNetworkError() {
        Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int viewPostsVisibility() {
        return View.GONE;
    }

    @Override
    public View.OnClickListener viewPosts(Subreddit subreddit) {
        return null;
    }

    private void showErrorText() {
        Toast.makeText(this.getContext(), getString(R.string.loading_subreddits_error), Toast.LENGTH_SHORT)
                .show();
        fragmentSubredditsBinding.tvNoSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.rvFragmentSubreddits.setVisibility(View.GONE);
    }

    private void showSubreddits() {
        fragmentSubredditsBinding.tvNoSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.tvNoSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.GONE);
        fragmentSubredditsBinding.rvFragmentSubreddits.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        fragmentSubredditsBinding.pbSubreddits.setVisibility(View.VISIBLE);
        fragmentSubredditsBinding.rvFragmentSubreddits.setVisibility(View.GONE);
    }
}
