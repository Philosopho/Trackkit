package com.krinotech.trackkit.doubles;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.krinotech.data.Subreddit;
import com.krinotech.data.contract.TrackkitDao;

import java.util.List;

public class TrackkitDaoDouble implements TrackkitDao {
    LiveData<List<Subreddit>> newSubreddits;

    @Override
    public LiveData<List<Subreddit>> loadNewSubreddits() {
        return newSubreddits;
    }

    @Override
    public void insertNewSubreddits(List<Subreddit> subreddits) {
        newSubreddits = new MutableLiveData<>(subreddits);
    }

    @Override
    public void updateSubreddit(Subreddit subreddit) {
        String id = subreddit.getId();
        List<Subreddit> subreddits = newSubreddits.getValue();
        if(subreddits != null) {
            for(Subreddit newSubreddit: subreddits) {
                if(newSubreddit.getId() == id) {
                    newSubreddit.setIsTracking(subreddit.isTracking());
                }
            }
        }
    }
}
