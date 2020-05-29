package com.krinotech.data.contract;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RedditApi {

    @GET(ApiContract.NEW_SUBREDDITS_ENDPOINT)
    Call<JsonElement> getNewSubreddits();

    @GET(ApiContract.POPULAR_SUBREDDITS_ENDPOINT)
    Call<JsonElement> getPopularSubreddits();

    @GET(ApiContract.DEFAULT_SUBREDDITS_ENDPOINT)
    Call<JsonElement> getDefaultSubreddits();

    @GET("{subredditUrl}" + ApiContract.POSTS_SUBREDDITS_ENDPOINT)
    Call<JsonElement> getPostsOfSubreddit(@Path("subredditUrl") String subredditUrl);

    @GET("{postUrl}")
    Call<JsonElement> getPostContents(@Path("postUrl") String postUrl);
}
