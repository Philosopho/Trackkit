package com.krinotech.trackkit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krinotech.data.SubredditPost;
import com.krinotech.data.contract.Repository;

import java.util.List;

public class PostsViewModel extends ViewModel {
    private LiveData<List<SubredditPost>> subredditPosts;
    private Repository repository;


    public PostsViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<SubredditPost>> getSubredditPosts(String redditEndpoint, String subredditId) {
        subredditPosts = repository.getSubredditPosts(redditEndpoint, subredditId);
        return subredditPosts;
    }


}
