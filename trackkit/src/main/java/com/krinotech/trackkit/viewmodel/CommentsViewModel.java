package com.krinotech.trackkit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.krinotech.data.PostContent;
import com.krinotech.data.contract.Repository;

public class CommentsViewModel extends ViewModel {
    private Repository repository;

    public CommentsViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<PostContent> getSubredditPosts(String postUrl, String subredditId) {
        return repository.getPostContents(postUrl, subredditId);
    }


}
