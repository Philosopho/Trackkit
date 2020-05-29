package com.krinotech.trackkit;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.krinotech.trackkit.screen.MainScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewSubreddits {

    private MainScreen mainScreen;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(
            MainActivity.class, false, true);

    @Before
    public void setUp() {
        mainScreen = new MainScreen();
    }

    @Test
    public void launch_allSubreddits_shouldBeDisplayed() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Test
    public void launch_subredditsHeadline_shouldBeHighlighted() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Test
    public void launch_shouldShowCorrectTracking() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Test
    public void swipeRight_subredditsHeadline_shouldNotBeHighlighted() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Test
    public void tapUnTrackedBox_shouldNowShowAsTracking() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Test
    public void tapTrackedBox_shouldNowShowAsUnTracking() {
        throw new UnsupportedOperationException("Not yet Implemented");
    }


}
