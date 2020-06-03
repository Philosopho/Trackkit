package com.krinotech.trackkit;

import android.app.Instrumentation;
import android.content.res.Resources;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.krinotech.trackkit.application.TrackkitApplicationMock;
import com.krinotech.trackkit.screen.MainScreen;
import com.krinotech.trackkit.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewSubreddits {
    private MainScreen mainScreen;
    private IdlingResource idlingResource;
    private IdlingResource picassoIdlingResource;
    private Resources resources;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(
            MainActivity.class, false, false);
    private TrackkitApplicationMock trackkitApplicationMock;

    @Before
    public void setUp() {
        TestHelper.rateLimiterHoursPassed = true;
        mainScreen = new MainScreen();

        Instrumentation instrumentation = InstrumentationRegistry
                .getInstrumentation();
        trackkitApplicationMock = (TrackkitApplicationMock) instrumentation
                .getTargetContext().getApplicationContext();
        resources = trackkitApplicationMock.getResources();

        idlingResource = trackkitApplicationMock.getIdlingResource();
        picassoIdlingResource = DataBindingHelper.idlingResource;
        IdlingRegistry.getInstance().register(idlingResource, picassoIdlingResource);
    }

    @After
    public void tearDown() {
        if(idlingResource != null && picassoIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource, picassoIdlingResource);
        }
        TestHelper.trackedIds.clear();
    }

    @Test
    public void launch_allSubreddits_shouldBeDisplayed() {
        TestHelper.trackedIds.add((long) 1);
        TestHelper.trackedIds.add((long) 2);
        TestHelper.trackedIds.add((long) 3);

        testRule.launchActivity(null);

        mainScreen.scrollAndVerify(testRule.getActivity().getSubreddits());
    }

    @Test
    public void launch_tabLayout_shouldHaveTabTitlesInCorrectPositions() {

        String expectSubredditsTitle = resources.getString(R.string.subreddits_fragment_title);
        String expectTrackingTitle = resources.getString(R.string.tracking_fragment_title);
        String expectPreferenceTitle = resources.getString(R.string.preferences_fragment_title);

        testRule.launchActivity(null);

        mainScreen.verifyTabTitle(expectSubredditsTitle, 0);
        mainScreen.verifyTabTitle(expectTrackingTitle, 1);
        mainScreen.verifyTabTitle(expectPreferenceTitle, 2);
    }

    @Test
    public void launch_tabLayout_shouldHaveSubredditTitleHighlighted() {
        testRule.launchActivity(null);
        int subredditsPosition = 0;

        mainScreen.verifyTabIsHighlighted(subredditsPosition);
    }

    @Test
    public void tapExpand_Collapse_verifyButtonTitles_verifyMaxLines() {
        testRule.launchActivity(null);

        int position = 0;
        String expand = resources.getString(R.string.expand_subreddit_btn);


        mainScreen.tapDescriptionButtonAtPosition(position);

        String collapse = resources.getString(R.string.collapse_subreddit_btn);

        mainScreen.verifyRecyclerViewItemText(collapse, position);

        int expectedMaxLines = resources.getInteger(R.integer.expanded_description_max_lines);

        mainScreen.verifyMaxLines(position, expectedMaxLines);

        mainScreen.tapDescriptionButtonAtPosition(position);

        expectedMaxLines = resources.getInteger(R.integer.initial_description_max_lines);

        mainScreen.verifyRecyclerViewItemText(expand, position);
        mainScreen.verifyMaxLines(position, expectedMaxLines);

    }

    @Test
    public void swipeLeft_subredditsHeadline_shouldNotBeHighlighted() {
        testRule.launchActivity(null);

        mainScreen.swipeLeft();

        mainScreen.verifyTabIsNotHighlighted(0);
    }

    @Test
    public void tapUnTrackedBox_shouldNowShowAsTracking() {
        testRule.launchActivity(null);

        mainScreen.tapCheckboxButtonAtPosition(0);

        mainScreen.verifyCheckboxIsTrackingImage(0);
    }

    @Test
    public void tapTrackedBox_shouldNowShowAsUnTracking() {
        TestHelper.trackedIds.add((long) 0);

        testRule.launchActivity(null);

        mainScreen.tapCheckboxButtonAtPosition(0);

        mainScreen.verifyCheckboxIsNotTrackingImage(0);
    }

    @Test
    public void tapTrackBox_shouldShowInTrackingScreen() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


}
