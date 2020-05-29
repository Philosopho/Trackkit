package com.krinotech.trackkit.doubles;

import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.trackkit.TestHelper;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TrackkitUserPreferencesDouble implements TrackkitPreferences {

    @Inject
    public TrackkitUserPreferencesDouble() { }

    @Override
    public boolean newSubredditsNeedsRefresh() {
        return TestHelper.rateLimiterHoursPassed;
    }

    @Override
    public void addSubredditToTracking(String subredditId) {
        TestHelper.trackedIds.add(subredditId);
    }

    @Override
    public void removeSubredditFromTracking(String subredditId) {
        TestHelper.trackedIds.remove(subredditId);
    }

    @Override
    public boolean isTracked(String subredditId) {
        Set<String> trackedSubreddits = TestHelper.trackedIds;
        return trackedSubreddits.contains(subredditId);
    }
}
