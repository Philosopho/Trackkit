package com.krinotech.trackkit.matcher;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TextViewMatcher {

    public static Matcher<View> hasMaxLines(int position, int expectedMaxLines, int textViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder =
                        item.findViewHolderForAdapterPosition(position);

                if(viewHolder == null) return false;

                TextView textView = viewHolder.itemView.findViewById(textViewId);

                if(textView == null) return false;

                return expectedMaxLines == textView.getMaxLines();
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}
