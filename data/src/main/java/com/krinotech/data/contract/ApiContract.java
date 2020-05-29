package com.krinotech.data.contract;

public interface ApiContract {
    String GRANT_TYPE_PARAM = "grant_type";
    String DEVICE_ID_PARAM = "device_id";
    String CLIENT_ID = "vzZyq-odJxyryA";
    String DEVICE_ID = "DO_NOT_TRACK_THIS_DEVICE";
    String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";
    String BASE_OAUTH2_URL = "https://oauth.reddit.com/";
    String BASE_REDDIT_URL = "https://www.reddit.com/";
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String CONTENT_TYPE = "application/x-www-form-urlencoded";

    String REQUEST_HEADER_NAME = "Accept";
    String REQUEST_HEADER_TYPE = "application/json";

    String AUTH_ENDPOINT = "/api/v1/access_token";
    String NEW_SUBREDDITS_ENDPOINT = "subreddits/new";
    String POPULAR_SUBREDDITS_ENDPOINT = "subreddits/popular";
    String DEFAULT_SUBREDDITS_ENDPOINT = "subreddits/default";
    String POSTS_SUBREDDITS_ENDPOINT = "/new";
}
