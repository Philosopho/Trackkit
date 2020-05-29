package com.krinotech.trackkit.screen;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.krinotech.data.Subreddit;
import com.krinotech.trackkit.BuildConfig;
import com.krinotech.trackkit.DataBindingHelper;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.TestHelper;
import com.krinotech.trackkit.matcher.DrawableMatcher;
import com.krinotech.trackkit.matcher.LayoutParamsMatcher;
import com.krinotech.trackkit.matcher.PagerTabMatcher;
import com.krinotech.trackkit.matcher.RecyclerViewItemAction;
import com.krinotech.trackkit.matcher.TextViewMatcher;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.krinotech.trackkit.matcher.LayoutParamsMatcher.atPosition;
import static org.hamcrest.Matchers.not;

public class MainScreen {

    public void verifyRecyclerViewItemText(String text, int index) {
        onView(withId(R.id.rv_fragment_subreddits))
                .check(matches(atPosition(index, hasDescendant(withText(text)))));
    }


    public void verifyTabTitle(String title, int position) {
        onView(withId(R.id.tab_layout))
                .check(matches(PagerTabMatcher.hasTitleAtPosition(title, position)));
    }

    public void verifyTabIsHighlighted(int position) {
        onView(withId(R.id.tab_layout))
                .check(matches(PagerTabMatcher.isHighlightedAtPosition(position)));
    }
    public void verifyTabIsNotHighlighted(int position) {
        onView(withId(R.id.tab_layout))
                .check(matches(not(PagerTabMatcher.isHighlightedAtPosition(position))));
    }

    public void verifyMaxLines(int position, int expectedMaxLines) {
        onView(withId(R.id.rv_fragment_subreddits))
                .check(matches(TextViewMatcher
                        .hasMaxLines(position, expectedMaxLines,
                        R.id.subreddit_description)));
    }

    public void tapDescriptionButtonAtPosition(int position) {
        tapButtonAtPositionWithId(position, R.id.view_comments);
    }

    public void tapCheckboxButtonAtPosition(int position) {
        tapButtonAtPositionWithId(position, R.id.subreddit_checkbox);
    }

    private void tapButtonAtPositionWithId(int position, int id) {
        onView(withId(R.id.rv_fragment_subreddits))
                .perform(RecyclerViewActions.scrollToPosition(position))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(
                                position,
                                RecyclerViewItemAction
                                        .clickViewWithId(id)
                        )
                );
    }

    public void swipeLeft() {
        onView(withId(R.id.pager)).perform(ViewActions.swipeLeft());
    }


    public void scrollAndVerify(List<Subreddit> subreddits) {
        ViewInteraction recyclerView = onView(withId(R.id.rv_fragment_subreddits));
        for(int index = 0; index < subreddits.size(); index++) {
            recyclerView
                    .perform(RecyclerViewActions.scrollToPosition(
                            index)
                    );

            Subreddit subreddit = subreddits.get(index);

            verifyRecyclerViewItemText(subreddit.getTitle(), index);
            verifyRecyclerViewItemText(subreddit.getUrl(), index);
            verifyRecyclerViewItemText(subreddit.getSubscribersFormatted(), index);
            verifyRecyclerViewItemText(subreddit.getDescription(), index);
            verifyRecyclerViewItemText(subreddit.getSubredditType(), index);
            verifyCheckboxImage(index);
            verifyImage(recyclerView, index);
        }
    }

    private void verifyCheckboxImage(int index) {
        if(BuildConfig.FLAVOR.contains("mock")) {
            if(TestHelper.trackedIds.contains((long) index)) {
                verifyRecyclerViewImageDrawable(index,
                        R.drawable.baseline_check_circle_black_48, R.id.subreddit_checkbox);
            }
            else {
                verifyRecyclerViewImageDrawable(index,
                        R.drawable.baseline_check_circle_outline_black_48, R.id.subreddit_checkbox);
            }
        }
        else {
            if(TestHelper.trackedIds.contains((long) index)) {
                verifyRecyclerViewImageDrawable(index,
                        R.drawable.baseline_check_circle_black_48, R.id.subreddit_checkbox);
            }
            else {
                verifyRecyclerViewImageDrawable(index,
                        R.drawable.baseline_check_circle_outline_black_48, R.id.subreddit_checkbox);
            }
        }
    }

    private void verifyImage(ViewInteraction recyclerView, int index) {
        if(DataBindingHelper.noImageLoaded) {
            verifyRecyclerViewImageDrawable(
                    index, DrawableMatcher.EMPTY, R.id.post_thumbnail);
            int[] expectedLayoutParams = {0, 0, 0, 0};
            verifyRecyclerViewLayoutParams(recyclerView, index, expectedLayoutParams, R.id.post_thumbnail);
        }
        else {
            verifyRecyclerViewImageDrawable(
                    index, DrawableMatcher.ANY, R.id.post_thumbnail
            );
        }
    }

    private void verifyRecyclerViewLayoutParams(ViewInteraction recyclerView, int index,
                                                int[] expectedLayoutParams, int viewId) {
        recyclerView.check(matches(LayoutParamsMatcher
                .hasMarginLayoutParams(index, expectedLayoutParams, viewId)));

    }

    private void verifyRecyclerViewImageDrawable(int index,
                                                int expectedId, int expectedImage) {
        onView(withId(R.id.rv_fragment_subreddits)).check(matches(DrawableMatcher
                .hasDrawable(index, expectedId, expectedImage)));
    }

    public void verifyCheckboxIsTrackingImage(int index) {
        verifyRecyclerViewImageDrawable(
                index,
                R.drawable.baseline_check_circle_black_48,
                R.id.subreddit_checkbox
        );
    }

    public void verifyCheckboxIsNotTrackingImage(int index) {
        verifyRecyclerViewImageDrawable(
                index,
                R.drawable.baseline_check_circle_outline_black_48,
                R.id.subreddit_checkbox
        );
    }
}
