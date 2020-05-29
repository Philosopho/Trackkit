package com.krinotech.data;

import android.content.SharedPreferences;

import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.DateHelper;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackkitUserPreferencesTest {
    private SharedPreferences sharedPreferencesMock;
    private SharedPreferences.Editor editorMock;
    private DateHelper dateHelper;

    private TrackkitUserPreferences testSubject;
    private final String PREVIOUS_TIME = TrackkitUserPreferences.PREVIOUS_TIME_NEW;
    private long currentDate;

    @Before
    public void setUp() {
        Calendar calendarMock = mock(Calendar.class);
        sharedPreferencesMock = mock(SharedPreferences.class);
        editorMock = mock(SharedPreferences.Editor.class);
        dateHelper = mock(DateHelper.class);

        currentDate = Calendar.getInstance().getTimeInMillis();


        testSubject = new TrackkitUserPreferences(sharedPreferencesMock, dateHelper);

        when(dateHelper.getCurrentDate()).thenReturn(currentDate);
        when(sharedPreferencesMock.edit()).thenReturn(editorMock);

        when(editorMock.putLong(any(String.class), any(Long.class))).thenReturn(editorMock);
        doNothing().when(editorMock).apply();
    }

    @Test
    public void newSubredditsNeedsRefresh_NoPreviousDay_shouldReturnTrue() {
        when(sharedPreferencesMock.contains(PREVIOUS_TIME)).thenReturn(false);

        boolean result = testSubject.subredditsNeedToRefresh();

        verify(editorMock, times(1))
                .putLong(PREVIOUS_TIME, currentDate);

        verify(editorMock, times(1)).apply();

        assertTrue(result);
    }

    @Test
    public void newSubredditsNeedsRefresh_PassedHoursIsFalse_shouldReturnFalse() {
        long previousDay = 100000000L;

        when(sharedPreferencesMock.contains(PREVIOUS_TIME))
                .thenReturn(true);
        when(sharedPreferencesMock.getLong(PREVIOUS_TIME, 0)).thenReturn(previousDay);
        when(dateHelper.hoursPassed(previousDay, TrackkitUserPreferences.HOURS_TO_PASS))
                .thenReturn(false);

        boolean result = testSubject.subredditsNeedToRefresh();

        verify(editorMock, times(0))
                .putLong(PREVIOUS_TIME, currentDate);

        verify(editorMock, times(0)).apply();

        assertFalse(result);
    }

    @Test
    public void newSubredditsNeedsRefresh_PassedHoursIsTrue_shouldReturnFalse() {
        long previousDay = 100000000L;

        when(sharedPreferencesMock.contains(PREVIOUS_TIME))
                .thenReturn(true);
        when(sharedPreferencesMock.getLong(PREVIOUS_TIME, 0)).thenReturn(previousDay);
        when(dateHelper.hoursPassed(previousDay, TrackkitUserPreferences.HOURS_TO_PASS))
                .thenReturn(true);

        boolean result = testSubject.subredditsNeedToRefresh();

        verify(editorMock, times(1))
                .putLong(PREVIOUS_TIME, currentDate);

        verify(editorMock, times(1)).apply();

        assertTrue(result);
    }

    @Test
    public void addSubredditToTracking_nullPreferences() {
        String id = "2";

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add(id);

        when(editorMock.putStringSet(eq(TrackkitPreferences.TRACKING_IDS), anySet())).thenReturn(editorMock);
        doNothing().when(editorMock).apply();

        testSubject.addSubredditToTracking(id);

        verify(editorMock, times(1)).putStringSet(TrackkitPreferences.TRACKING_IDS, expectedSet);
        verify(editorMock, times(1)).apply();

    }

    @Test
    public void addSubredditToTracking_keyHasValues() {
        String id = "2";
        String id2 = "1";

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add(id);
        expectedSet.add(id2);

        Set<String> returnedSet = new HashSet<>();
        returnedSet.add(id2);
        String key = TrackkitPreferences.TRACKING_IDS;

        when(sharedPreferencesMock.getStringSet(eq(key), anySet())).thenReturn(returnedSet);
        when(editorMock.putStringSet(eq(key), anySet())).thenReturn(editorMock);
        doNothing().when(editorMock).apply();

        testSubject.addSubredditToTracking(id);

        verify(editorMock, times(1)).putStringSet(key, expectedSet);
        verify(editorMock, times(1)).apply();
    }

    @Test
    public void removeSubredditFromTracking_returnedWithValues() {
        String id1 = "2";
        String id2 = "1";

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add(id2);

        Set<String> returnedSet = new HashSet<>();
        returnedSet.add(id1);
        returnedSet.add(id2);
        String key = TrackkitPreferences.TRACKING_IDS;

        when(sharedPreferencesMock.getStringSet(eq(key), anySet())).thenReturn(returnedSet);
        when(editorMock.putStringSet(eq(key), anySet())).thenReturn(editorMock);
        doNothing().when(editorMock).apply();

        testSubject.removeSubredditFromTracking(id1);

        verify(editorMock, times(1)).putStringSet(key, expectedSet);
        verify(editorMock, times(1)).apply();
    }

    @Test
    public void removeSubredditFromTracking_nullPreferences() {
        String id = "2";

        Set<String> expectedSet = new HashSet<>();

        when(editorMock.putStringSet(eq(TrackkitPreferences.TRACKING_IDS), anySet())).thenReturn(editorMock);
        doNothing().when(editorMock).apply();

        testSubject.removeSubredditFromTracking(id);

        verify(editorMock, times(1)).putStringSet(TrackkitPreferences.TRACKING_IDS, expectedSet);
        verify(editorMock, times(1)).apply();
    }

    @Test
    public void isTracked_containsId_shouldReturnTrue() {
        String id1 = "1";

        Set<String> returnedSet = new HashSet<>();
        returnedSet.add(id1);
        String key = TrackkitPreferences.TRACKING_IDS;

        when(sharedPreferencesMock.getStringSet(eq(key), anySet())).thenReturn(returnedSet);

        assertTrue(testSubject.isTracked(id1));
    }

    @Test
    public void isTracked_doesNotContainId_shouldReturnFalse() {
        String id1 = "1";
        String id2 = "2";

        Set<String> returnedSet = new HashSet<>();
        returnedSet.add(id1);
        String key = TrackkitPreferences.TRACKING_IDS;

        when(sharedPreferencesMock.getStringSet(eq(key), anySet())).thenReturn(returnedSet);

        assertFalse(testSubject.isTracked(id2));
    }
}