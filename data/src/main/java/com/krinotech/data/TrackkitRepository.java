package com.krinotech.data;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonElement;
import com.krinotech.data.contract.ApiIdlingResource;
import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.Repository;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.SubredditType;
import com.krinotech.domain.ThreadExecutor;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class TrackkitRepository implements Repository {
    private RedditApi redditApi;
    private ThreadExecutor threadExecutor;
    private TrackkitDao trackkitDao;
    private TrackkitPreferences trackkitPreferences;
    private ApiIdlingResource apiIdlingResource;

    @Inject
    public TrackkitRepository(RedditApi redditApi,
                              ThreadExecutor threadExecutor,
                              TrackkitDao trackkitDao,
                              TrackkitPreferences trackkitPreferences,
                              ApiIdlingResource apiIdlingResource
                      ) {
        this.redditApi = redditApi;
        this.threadExecutor = threadExecutor;
        this.trackkitDao = trackkitDao;
        this.trackkitPreferences = trackkitPreferences;
        this.apiIdlingResource = apiIdlingResource;
    }

    public LiveData<List<Subreddit>> getSubreddits(SubredditType subredditType) {
        refreshData(subredditType);

        return trackkitDao.loadSubreddits(subredditType.name());
    }

    @Override
    public LiveData<List<Subreddit>> getTrackingSubreddits() {
        return trackkitDao.loadTrackingSubreddits();
    }

    @Override
    public boolean updateSubredditTracking(Subreddit subreddit) {
        String id = subreddit.getId();
        boolean added = false;

        if(trackkitPreferences.isTracked(id)){
            trackkitPreferences.removeSubredditFromTracking(id);
        }
        else {
            trackkitPreferences.addSubredditToTracking(id);
            added = true;
        }
        final boolean trackedStatus = added;
        threadExecutor.diskIO().execute(() -> {
            subreddit.setIsTracking(trackedStatus);
            trackkitDao.updateSubreddit(subreddit);
        });
        return added;
    }

    @Override
    public LiveData<List<SubredditPost>>
    getSubredditPosts(String redditEndpoint, String subredditId) {
        refreshData(redditEndpoint, subredditId);
        return trackkitDao.getSubredditPostsWithId(subredditId);
    }

    @Override
    public LiveData<PostContent> getPostContents(String postUrl, String subredditPostId) {
        refreshCommentsData(postUrl, subredditPostId);
        return trackkitDao.loadPostContent(subredditPostId);
    }


    private void refreshData(SubredditType subredditType) {
        apiIdlingResource.setIdleState(false);
        threadExecutor
                .diskIO()
                .execute(createRunnable(subredditType));
    }

    private void refreshData(String subredditUrl, String subredditId) {
        apiIdlingResource.setIdleState(false);
        threadExecutor
                .diskIO()
                .execute(createRunnablePost(subredditUrl, subredditId));
    }

    private void refreshCommentsData(String postUrl, String subredditPostId) {
        apiIdlingResource.setIdleState(false);
        threadExecutor
                .diskIO()
                .execute(createRunnablePostComments(postUrl, subredditPostId));
    }

    private Runnable createRunnable(final SubredditType subredditType) {
        final boolean needsRefresh = trackkitPreferences.subredditsNeedToRefresh(subredditType);
        setNetworkLoading(needsRefresh);
        return () -> {
            if(needsRefresh) {
                Call<JsonElement> call = getJsonElementCall(subredditType);
                try {
                    JsonElement jsonElement = call.execute().body();
                    List<Subreddit> subreddits = JsonUtil.convertSubredditsFromJSON(subredditType, jsonElement, trackkitPreferences);

                    DataPresenter.setNetworkLoading(false);
                    trackkitDao.insertSubreddits(subreddits);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            apiIdlingResource.setIdleState(true);
        };
    }

    private Runnable createRunnablePost(final String subredditUrl, final String subredditId) {
        final boolean needsRefresh = trackkitPreferences.postsNeedToRefresh(subredditId);
        setNetworkLoading(needsRefresh);
        return () -> {
            if(needsRefresh) {
                Call<JsonElement> call = redditApi.getPostsOfSubreddit(subredditUrl);
                try {
                    JsonElement jsonElement = call.execute().body();
                    List<SubredditPost> subredditPosts = JsonUtil.convertSubredditPostsFromJson(subredditId, jsonElement);

                    DataPresenter.setNetworkLoading(false);
                    trackkitDao.insertSubredditPosts(subredditPosts);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            apiIdlingResource.setIdleState(true);
        };
    }

    private Runnable createRunnablePostComments(final String postUrl, final String subredditPostId) {
        final boolean needsRefresh = trackkitPreferences.postCommentsNeedToRefresh(subredditPostId);
        setNetworkLoading(needsRefresh);
        return () -> {
            if(needsRefresh) {
                Call<JsonElement> call = redditApi.getPostContents(postUrl);
                try {
                    JsonElement jsonElement = call.execute().body();
                    List<PostComment> postComments = JsonUtil.convertChildrenCommentsJSON(jsonElement, subredditPostId);
                    PostTitle postTitle = JsonUtil.convertJsonToPostTitle(jsonElement, subredditPostId);

                    DataPresenter.setNetworkLoading(false);
                    trackkitDao.insertPostTitle(postTitle);
                    trackkitDao.insertPostComments(postComments);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            apiIdlingResource.setIdleState(true);
        };
    }

    private void setNetworkLoading(boolean needsRefresh) {
        if(!needsRefresh) { DataPresenter.setNetworkLoading(false); }
        else { DataPresenter.setNetworkLoading(true); }
    }

    private Call<JsonElement> getJsonElementCall(SubredditType subredditType) {
        switch (subredditType) {
            case NEW:
                return redditApi.getNewSubreddits();
            case POPULAR:
                return redditApi.getPopularSubreddits();
            default:
                return redditApi.getDefaultSubreddits();
        }
    }
}
