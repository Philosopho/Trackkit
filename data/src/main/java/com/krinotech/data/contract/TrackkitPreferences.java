package com.krinotech.data.contract;

import com.krinotech.domain.SubredditType;

public interface TrackkitPreferences {
    String SUBREDDITS_SELECTED = "SUBREDDITS_SELECTED";
    String TOKEN_TIME = "TOKEN_TIME";
    String PREFERENCES_NAME = "USER";
    String PREVIOUS_TIME_NEW = "PREVIOUS_TIME";
    String PREVIOUS_TIME_DEFAULT = "DEFAULT_PREVIOUS_TIME";
    String PREVIOUS_TIME_POPULAR = "POPULAR_PREVIOUS_TIME";
    String TRACKING_IDS = "TRACKING_IDS";

    int HOURS_TO_PASS = 2;
    String TOKEN_KEY = "TOKEN_KEY";

    boolean subredditsNeedToRefresh(SubredditType subredditType);

    void addSubredditToTracking(String subredditId);

    void removeSubredditFromTracking(String subredditId);

    boolean isTracked(String subredditId);

    String getAccessToken();

    void saveAccessTokenAndExpiration(String accessToken, long expiration);

    boolean tokenNeedsRefresh();

    void saveSelected(SubredditType selected);

    SubredditType getCurrentSelected();

    boolean postsNeedToRefresh(String subredditUrl);

    boolean postCommentsNeedToRefresh(String id);
}
