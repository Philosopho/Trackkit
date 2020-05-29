package com.krinotech.data.contract;

import androidx.lifecycle.LiveData;

import com.krinotech.data.PostContent;
import com.krinotech.data.Subreddit;
import com.krinotech.data.SubredditPost;
import com.krinotech.domain.SubredditType;

import java.util.List;

public interface Repository {

    LiveData<List<Subreddit>> getSubreddits(SubredditType subredditType);

    LiveData<List<Subreddit>> getTrackingSubreddits();

    boolean updateSubredditTracking(Subreddit subreddit);

    LiveData<List<SubredditPost>> getSubredditPosts(String redditEndpoint, String subredditId);

    LiveData<PostContent> getPostContents(String postUrl, String subredditPostId);
}
