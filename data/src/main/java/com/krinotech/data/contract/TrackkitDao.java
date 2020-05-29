package com.krinotech.data.contract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.krinotech.data.PostComment;
import com.krinotech.data.PostContent;
import com.krinotech.data.PostTitle;
import com.krinotech.data.Subreddit;
import com.krinotech.data.SubredditPost;

import java.util.List;

@Dao
public interface TrackkitDao {

    @Query("SELECT * FROM subreddit WHERE type == :subredditType")
    LiveData<List<Subreddit>> loadSubreddits(String subredditType);

    @Query("SELECT * FROM subreddit WHERE isTracking = 1")
    LiveData<List<Subreddit>> loadTrackingSubreddits();

    @Query("SELECT * FROM subredditPost WHERE subredditId == :subredditId")
    LiveData<List<SubredditPost>> getSubredditPostsWithId(String subredditId);

    @Transaction
    @Query("SELECT * FROM postTitle WHERE subredditPostId == :subredditPostId")
    LiveData<PostContent> loadPostContent(String subredditPostId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubreddits(List<Subreddit> subreddits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubredditPosts(List<SubredditPost> subredditPosts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSubreddit(Subreddit subreddit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPostComments(List<PostComment> postComment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPostTitle(PostTitle postTitle);
}
