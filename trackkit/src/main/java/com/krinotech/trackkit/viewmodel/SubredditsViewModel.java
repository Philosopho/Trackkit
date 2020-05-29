package com.krinotech.trackkit.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.krinotech.data.DataPresenter;
import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.Repository;
import com.krinotech.domain.SubredditType;

import java.util.List;

import javax.inject.Inject;

public class SubredditsViewModel extends ViewModel {
    private LiveData<List<Subreddit>> newSubreddits;
    private LiveData<List<Subreddit>> defaultSubreddits;
    private LiveData<List<Subreddit>> popularSubreddits;
    private LiveData<List<Subreddit>> trackingSubreddits;
    private Repository trackkitRepository;
    @Inject
    public SubredditsViewModel(Repository repository) {
        this.trackkitRepository = repository;
    }

    public LiveData<List<Subreddit>> getSubreddits(SubredditType subredditType) {
        switch (subredditType) {
            case NEW:
                if(newSubreddits == null) {
                    DataPresenter.loading(true);
                    newSubreddits = Transformations.map(trackkitRepository.getSubreddits(subredditType), list -> {
                        DataPresenter.loading(false);
                        return list;
                    });
                }
                return newSubreddits;
            case POPULAR:
                if(popularSubreddits == null) {
                    DataPresenter.loading(true);
                    popularSubreddits = Transformations.map(trackkitRepository.getSubreddits(subredditType), list -> {
                        DataPresenter.loading(false);
                        return list;
                    });
                }
                return popularSubreddits;
            default:
                if(defaultSubreddits == null) {
                    DataPresenter.loading(true);
                    defaultSubreddits = Transformations.map(trackkitRepository.getSubreddits(subredditType), list -> {
                        DataPresenter.loading(false);
                        return list;
                    });
                }
                return defaultSubreddits;
        }
    }

    public LiveData<List<Subreddit>> getTrackingSubreddits() {
        if(trackingSubreddits == null) {
            DataPresenter.loading(true);
            trackingSubreddits = Transformations.map(trackkitRepository.getTrackingSubreddits(), list -> {
                DataPresenter.loading(false);
                return list;
            });
            return trackingSubreddits;
        }
        return trackingSubreddits;
    }
    /**
     *
     * @param subreddit
     * @return true if subreddit added to tracking, false if removed from tracking
     */
    public boolean addOrRemoveSubredditFromTracking(Subreddit subreddit) {
        return trackkitRepository.updateSubredditTracking(subreddit);
    }

    public void removeAllObservers(LifecycleOwner lifecycleOwner) {
        if(newSubreddits != null && newSubreddits.hasObservers()) {
            newSubreddits.removeObservers(lifecycleOwner);
        }
        if(defaultSubreddits != null && defaultSubreddits.hasObservers()) {
            defaultSubreddits.removeObservers(lifecycleOwner);
        }
        if(popularSubreddits != null && popularSubreddits.hasObservers()) {
            popularSubreddits.removeObservers(lifecycleOwner);
        }
    }
}
