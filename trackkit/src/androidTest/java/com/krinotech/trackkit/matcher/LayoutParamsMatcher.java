package com.krinotech.trackkit.matcher;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Arrays;

import static androidx.core.util.Preconditions.checkNotNull;

public class LayoutParamsMatcher {

    /**
     * Checks expected margins params for view in target position of recycler view.
     * @param position
     * @param expectedParams
     * @param viewId
     * @return
     */
    public static Matcher<View> hasMarginLayoutParams(final int position,
                                                      final int[] expectedParams,
                                                      final int viewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private int[] resultLayoutParams;
            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);

                if(viewHolder == null) return false;

                View view = viewHolder.itemView.findViewById(viewId);
                ViewGroup.MarginLayoutParams marginLayoutParams =
                        (ViewGroup.MarginLayoutParams) view.getLayoutParams();

                resultLayoutParams = new int[]{
                        marginLayoutParams.leftMargin,
                        marginLayoutParams.topMargin,
                        marginLayoutParams.rightMargin,
                        marginLayoutParams.bottomMargin
                };

                return Arrays.equals(expectedParams, resultLayoutParams);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with view with id: ");
                description.appendValue(viewId);
                description.appendText(" does not have layout params: ");
                description.appendValue(expectedParams);
                if(resultLayoutParams != null) {
                    description.appendText("[");
                    description.appendValue(resultLayoutParams);
                    description.appendText("]");
                }
            }
        };
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
