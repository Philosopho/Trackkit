package com.krinotech.trackkit.matcher;

import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;

import com.google.android.material.tabs.TabLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class PagerTabMatcher {

    public static Matcher<View> hasTitleAtPosition(String title, int position) {
        return new BoundedMatcher<View, TabLayout>(TabLayout.class) {
            private String tabTitle;

            @Override
            protected boolean matchesSafely(TabLayout item) {
                TabLayout.Tab tab = item.getTabAt(position);
                if(tab == null) {
                    return false;
                }
                tabTitle = (String) tab.getText();
                return title.equals(tabTitle);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("tab title at position ");
                description.appendValue(position);
                description.appendText("does not contain the title ");
                description.appendValue(title);
                if(tabTitle != null) {
                    description.appendText(" instead contains ");
                    description.appendValue(tabTitle);
                }
            }
        };
    }

    public static Matcher<View> isHighlightedAtPosition(int position) {
        return new BoundedMatcher<View, TabLayout>(TabLayout.class) {
            private Boolean isSelected;

            @Override
            protected boolean matchesSafely(TabLayout item) {
                TabLayout.Tab tab = item.getTabAt(position);
                if(tab == null) {
                    return false;
                }
                isSelected = tab.isSelected();
                return isSelected;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("tab at position ");
                description.appendValue(position);
                description.appendText("is currently selected is true ");
                if(isSelected != null) {
                    description.appendText("instead is currently selected ");
                    description.appendValue(isSelected);
                }
            }
        };
    }
}
