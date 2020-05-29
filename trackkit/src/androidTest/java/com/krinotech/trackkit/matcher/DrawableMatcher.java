package com.krinotech.trackkit.matcher;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import javax.annotation.Nonnull;

public class DrawableMatcher {

    public static final int EMPTY = 0;
    public static final int ANY = -1;
    private static String resourceName;

    /**
     * expectedIds are the ids of the target drawables you expect to match.
     * EMPTY = 0 or ANY = -1 or Specific >= 1
     * Index 0 to 3 will match the left, top, right, bottom drawables, respectively.
     * @param expectedIds
     * @return BoundedMatcher of TextView
     */
    public static Matcher<View> hasDrawables(final @Nonnull int[] expectedIds) {
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            protected boolean matchesSafely(TextView textView) {
                Drawable[] drawables = textView.getCompoundDrawables();

                int size = drawables.length;

                for (int index = 0; index < size; index++) {
                    if (expectedIds[index] == EMPTY) {
                        if (drawables[index] != null) {
                            return false;
                        }
                    } else if (expectedIds[index] == ANY) {
                        if (drawables[index] == null) {
                            return false;
                        }
                    } else {

                        Drawable drawable = drawables[index];

                        Resources resources = textView.getContext().getResources();

                        if (!isDrawableMatch(expectedIds[index], resources, drawable)) {
                            return false;
                        }
                    }

                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                appendDescription(description, expectedIds);
            }
        };
    }

    /**
     * expectedId is the target drawable you expect to match
     * EMPTY = 0 or ANY = -1 or Specific >= 1
     * @param expectedId
     * @return BoundedMatcher of ImageView
     */
    public static Matcher<View> hasDrawable(final int expectedId){
        return new BoundedMatcher<View, ImageView>(ImageView.class) {

            @Override
            protected boolean matchesSafely(ImageView imageView) {
                Drawable drawable = imageView.getDrawable();

                if(expectedId == EMPTY) {
                    return drawable == null;
                }
                if(expectedId == ANY) {
                    return drawable != null;
                }

                Resources resources = imageView.getContext().getResources();

                return isDrawableMatch(expectedId, resources, drawable);
            }

            @Override
            public void describeTo(Description description) {
                appendDescription(description, expectedId);
            }
        };
    }

    /**
     * expectedId is the target drawable you expect to match
     * recyclerViewPositionIndex is the index
     * of the view holder in the recycler view adapter
     * EMPTY = 0 or ANY = -1 or Specific >= 1
     * @param recyclerViewPositionIndex
     * @param expectedId
     * @return BoundedMatcher of RecyclerView
     */
    public static Matcher<View> hasDrawable(final int recyclerViewPositionIndex, final int expectedId, int imageId){
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(recyclerViewPositionIndex);

                if(viewHolder == null) {
                    return false;
                }

                View view = viewHolder.itemView;
                ImageView imageView = view.findViewById(imageId);
                Drawable drawable = imageView.getDrawable();

                if(expectedId == EMPTY) {
                    resourceName = "None";
                    return drawable == null;
                }
                if(expectedId == ANY) {
                    resourceName = "Any";
                    return drawable != null;
                }

                Resources resources = imageView.getContext().getResources();

                return isDrawableMatch(expectedId, resources, drawable);
            }

            @Override
            public void describeTo(Description description) {
                appendDescription(description, expectedId);
            }
        };
    }

    private static void appendDescription(Description description, Object expectedId) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
        resourceName = null;
    }

    private static boolean isDrawableMatch(int expectedId, Resources resources, Drawable targetDrawable) {
        Drawable expectedDrawable = resources.getDrawable(expectedId);
        resourceName = resources.getResourceName(expectedId);

        if(expectedDrawable == null) {
            return false;
        }

        Bitmap bitmap = getBitMap(targetDrawable);
        Bitmap otherBitmap = getBitMap(expectedDrawable);
        otherBitmap.eraseColor(Color.BLACK);
        bitmap.eraseColor(Color.BLACK);

        return bitmap.sameAs(otherBitmap);
    }

    private static Bitmap getBitMap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
