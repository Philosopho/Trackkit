package com.krinotech.trackkit;

import android.view.View;
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
                        .fit()
                        .error(R.color.defaultBackground)
                        .placeholder(R.color.defaultBackground)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Timber.d("Picasso onSuccess");
                                noImageLoaded = false;
                                idlingResource.setIdleState(true);
                            }

                            @Override
                            public void onError(Exception e) {
                                Timber.d("onError");
                            }
                        });
            }
            catch (IllegalArgumentException exception) {
                Timber.d("onEmptyString");
                imageView.setBackground(imageView.getResources().getDrawable(R.color.defaultBackground));
            }
        }
    }
}
