package com.krinotech.data;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonElement;
import com.krinotech.data.contract.ApiIdlingResource;
import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.DirectExecutor;
import com.krinotech.domain.ThreadExecutor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.mock.Calls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrackkitRepositoryTest {

    @Mock
    RedditApi redditApiMock;

    @Mock
    ThreadExecutor threadExecutorMock;

    @Mock
    TrackkitDao trackkitDaoMock;

    @Mock
    TrackkitPreferences trackkitPreferencesMock;

    @Mock
    ApiIdlingResource apiIdlingResource;

    @Mock
    LiveData<List<Subreddit>> liveDataMock;

    private TrackkitRepository testSubject;

    @Before
    public void setUp() {
        Mockito.when(trackkitPreferencesMock.subredditsNeedToRefresh())
                .thenReturn(false);

        Mockito.when(threadExecutorMock.diskIO())
                .thenReturn(new DirectExecutor());

        Mockito.when(trackkitDaoMock.loadSubreddits()).thenReturn(liveDataMock);

        List<Subreddit> subreddits = new ArrayList<>();

        Mockito.when(redditApiMock.getNewSubreddits()).thenReturn(Calls.response(any(JsonElement.class)));

        testSubject = new TrackkitRepository(
                redditApiMock,
                threadExecutorMock,
                trackkitDaoMock,
                trackkitPreferencesMock,
                apiIdlingResource);
    }

    @Test
    public void getNewSubreddits_shouldReturnSubredditsFromDao() {
        LiveData<List<Subreddit>> resultLiveData = testSubject.getSubreddits();

        verify(threadExecutorMock,
                Mockito.times(1))
                .diskIO();

        verify(trackkitPreferencesMock, Mockito.times(1)).subredditsNeedToRefresh();


        verify(trackkitDaoMock, times(1)).loadSubreddits();

        assertEquals(liveDataMock, resultLiveData);
    }

    @Test
    public void getNewSubreddits_preferencesRefreshIsFalse_shouldNotCallMethods() {
        testSubject.getSubreddits();

        verify(threadExecutorMock,
                Mockito.times(1))
                .diskIO();

        verify(trackkitPreferencesMock, Mockito.times(1)).subredditsNeedToRefresh();

        verify(redditApiMock, Mockito.times(0)).getNewSubreddits();

        List<Subreddit> subreddits = new ArrayList<>();

        verify(trackkitDaoMock, times(0)).insertSubreddits(subreddits);
    }

    @Test
    public void getNewSubreddits_preferencesRefreshIsTrue_shouldNotCallMethods() {
        when(trackkitPreferencesMock.subredditsNeedToRefresh()).thenReturn(true);

        testSubject.getSubreddits();

        verify(threadExecutorMock,
                times(1))
                .diskIO();

        verify(trackkitPreferencesMock, times(1)).subredditsNeedToRefresh();

        verify(redditApiMock, times(1)).getNewSubreddits();

        List<Subreddit> subreddits = new ArrayList<>();

        verify(trackkitDaoMock, times(1)).insertSubreddits(subreddits);
    }

    @Test
    public void addOrRemoveSubredditFromTracking_isTracking_shouldRemove() {
        Subreddit subreddit = new Subreddit();

        when(trackkitPreferencesMock.isTracked(subreddit.getId())).thenReturn(true);
        boolean result = testSubject.updateSubredditTracking(subreddit);

        verify(trackkitPreferencesMock, times(1)).removeSubredditFromTracking(subreddit.getId());
        verify(trackkitDaoMock, times(1)).updateSubreddit(subreddit);

        assertFalse(result);
    }

    @Test
    public void addOrRemoveSubredditFromTracking_isNotTracking_shouldAdd() {
        Subreddit subreddit = new Subreddit();

        when(trackkitPreferencesMock.isTracked(subreddit.getId())).thenReturn(false);
        testSubject.updateSubredditTracking(subreddit);

        verify(trackkitPreferencesMock, times(1)).addSubredditToTracking(subreddit.getId());
        verify(trackkitDaoMock, times(1)).updateSubreddit(subreddit);

    }
}