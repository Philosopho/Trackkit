package com.krinotech.trackkit.dagger.module;

import android.app.Activity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.krinotech.trackkit.R;
import com.krinotech.trackkit.view.CommentsActivity;
import com.krinotech.trackkit.view.MainActivity;
import com.krinotech.trackkit.view.ViewPostsActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {FragmentModule.class, ServiceModule.class, ViewModelFactoryModule.class})
public interface ActivityModule {

    @ContributesAndroidInjector
    MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    ViewPostsActivity contributeViewPostsActivity();

    @ContributesAndroidInjector(
            modules = {ExoPlayerModule.class}
    )
    CommentsActivity contributeCommentsActivity();

    @Module
    abstract class ExoPlayerModule {

        @Binds
        abstract Activity bindCommentsActivity(CommentsActivity commentsActivity);

        @Provides
        static TrackSelector provideTrackSelector(Activity commentsActivity) {
            return new DefaultTrackSelector(commentsActivity);
        }

        @Provides
        static String provideUserAgent(Activity commentsActivity) {
            return Util.getUserAgent(commentsActivity, commentsActivity.getResources().getString(R.string.app_name));
        }

        @Provides
        static ExoPlayer provideExoPlayer(Activity commentsActivity, TrackSelector trackSelector, LoadControl loadControl) {
            return new SimpleExoPlayer
                    .Builder(commentsActivity).setLoadControl(loadControl)
                    .setTrackSelector(trackSelector).build();
        }

        @Provides
        static DefaultDataSourceFactory provideDefaultDataSourceFactory(Activity commentsActivity, String userAgent) {
            return new DefaultDataSourceFactory(commentsActivity, userAgent);
        }

        @Provides
        static ProgressiveMediaSource.Factory provideMediaSource(DefaultDataSourceFactory defaultDataSourceFactory) {
            return new ProgressiveMediaSource
                    .Factory(defaultDataSourceFactory);
        }

        @Provides
        static LoadControl bindLoadControl() {
            return new DefaultLoadControl();
        }

    }
}
