package com.krinotech.trackkit.matcher;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class RecyclerViewItemAction {

    public static ViewAction clickViewWithId(final int id) {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on child of RecyclerViewItem at position with specific id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View viewToClick = view.findViewById(id);
                viewToClick.performClick();
            }
        };
    }
}
