package com.krinotech.data;

import android.content.SharedPreferences;

import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.DateHelper;
import com.krinotech.domain.SubredditType;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrackkitUserPreferences implements TrackkitPreferences {
    private SharedPreferences sharedPreferences;
    private DateHelper dateHelper;

    @Inject
    public TrackkitUserPreferences(SharedPreferences sharedPreferences, DateHelper dateHelper) {
        this.sharedPreferences = sharedPreferences;
        this.dateHelper = dateHelper;
    }

    @Override
    public boolean subredditsNeedToRefresh(SubredditType subredditType) {
        String selected;
        switch (subredditType) {
            case POPULAR:
                selected = PREVIOUS_TIME_POPULAR;
                break;
            case DEFAULT:
                selected = PREVIOUS_TIME_DEFAULT;
                break;
            default:
                selected = PREVIOUS_TIME_NEW;
        }

        return timeToRefresh(selected);
    }

    @Override
    public void addSubredditToTracking(String subredditId) {
        String key = TrackkitPreferences.TRACKING_IDS;

        Set<String> trackingIds =
                new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));

        trackingIds.add(subredditId);

        sharedPreferences.edit().putStringSet(key, trackingIds).apply();
    }

    @Override
    public void removeSubredditFromTracking(String subredditId) {
        String key = TrackkitPreferences.TRACKING_IDS;

        Set<String> trackingIds =
                new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));

        trackingIds.remove(subredditId);

        sharedPreferences.edit().putStringSet(key, trackingIds).apply();
    }

    @Override
    public boolean isTracked(String subredditId) {
        Set<String> trackingIds =
                new HashSet<>(sharedPreferences
                                .getStringSet(
                                        TrackkitPreferences.TRACKING_IDS,
                                        new HashSet<>()
                                )
                );

       return trackingIds.contains(subredditId);
    }

    public String getAccessToken() {
        return sharedPreferences
                .getString(TOKEN_KEY, "");
    }

    public boolean tokenNeedsRefresh() {
        boolean previousTimeExists = sharedPreferences.contains(TOKEN_TIME);

        if(previousTimeExists) {
            long previousTime = sharedPreferences.getLong(TOKEN_TIME, 0);
            boolean notTimeToRefresh = !dateHelper.timesUp(previousTime);
            return !notTimeToRefresh;
        }
        return true;
    }

    @Override
    public void saveSelected(SubredditType selected) {
        sharedPreferences
                .edit()
                .putString(SUBREDDITS_SELECTED, selected.name())
                .apply();
    }

    @Override
    public SubredditType getCurrentSelected() {
        String selected = sharedPreferences.getString(SUBREDDITS_SELECTED, SubredditType.NEW.name());
        return SubredditType.valueOf(selected);
    }

    @Override
    public boolean postsNeedToRefresh(String subredditUrl) {
        return timeToRefresh(subredditUrl);
    }

    @Override
    public boolean postCommentsNeedToRefresh(String id) {
        return timeToRefresh(id);
    }

    public void saveAccessTokenAndExpiration(String token, long expiration) {
        long expirationInMilliSeconds = dateHelper.getCurrentDate() + (expiration * 1000);
        sharedPreferences
                .edit()
                .putString(TOKEN_KEY, token)
                .apply();

        sharedPreferences
                .edit()
                .putLong(TOKEN_TIME, expirationInMilliSeconds)
                .apply();
    }

    private boolean timeToRefresh(String selected) {
        boolean previousTimeExists = sharedPreferences.contains(selected);

        if(previousTimeExists) {
            long previousTime = sharedPreferences.getLong(selected, 0);
            boolean notTimeToRefresh = !dateHelper.hoursPassed(previousTime, HOURS_TO_PASS);
            if(notTimeToRefresh) {
                return false;
            }
        }
        sharedPreferences.edit()
                .putLong(selected, dateHelper.getCurrentDate())
                .apply();

        return true;
    }
}
