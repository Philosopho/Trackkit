package com.krinotech.trackkit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.krinotech.data.contract.ApiIdlingResource;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class DataBindingHelper {
    public static ApiIdlingResource idlingResource = new PicassoIdlingResource();
    public static boolean noImageLoaded = false;

    @BindingAdapter("app:imageUrl")
    public static void imageUrl(ImageView imageView, String url) {
        // TODO: Get default image in case there is no image.

        idlingResource.setIdleState(false);
        imageView.setVisibility(View.VISIBLE);
        if(url != null) {
            try {
                Picasso.get()
                        .load(url)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Timber.d("Picasso onSuccess");
                                noImageLoaded = false;
                                idlingResource.setIdleState(true);
                            }

                            @Override
                            public void onError(Exception e) {
                                // TODO: Some Images are not loading on emulator or smaller devices, but loads on physical
                                Timber.d("onError");
                                shrinkImageView(imageView);
                            }
                        });
            }
            catch (IllegalArgumentException exception) {
                Timber.d("onEmptyString");
                shrinkImageView(imageView);
            }
        }
    }

    private static void shrinkImageView(ImageView imageView) {
        Timber.d("Shrink Image View");
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();

        int subredditImageInvisible = (int) imageView
                .getResources()
                .getDimension(R.dimen.subredditImageHeightInvisible);

        layoutParams.height = subredditImageInvisible;
        marginLayoutParams.topMargin = subredditImageInvisible;

        imageView.setLayoutParams(layoutParams);
        imageView.setLayoutParams(marginLayoutParams);
        imageView.setImageDrawable(null);
        noImageLoaded = true;
        idlingResource.setIdleState(true);
    }
}
