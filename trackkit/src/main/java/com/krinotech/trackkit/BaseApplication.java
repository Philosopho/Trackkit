package com.krinotech.trackkit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.krinotech.data.contract.ApiIdlingResource;
import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.RedditAuthService;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.DateHelper;
import com.krinotech.trackkit.dagger.ApplicationComponent;
import com.krinotech.trackkit.dagger.DaggerApplicationComponent;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import timber.log.Timber;

public abstract class BaseApplication extends Application implements HasAndroidInjector {
    @Inject
    protected TrackkitPreferences trackkitPreferences;

    @Inject
    ApiIdlingResource idlingResource;

    @Inject
    ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public void onCreate() {
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        instantiateAppComponent();
        super.onCreate();
    }

    protected void instantiateAppComponent() {
        Timber.d("instantiateAppComponent");

        Executor executor = getExecutor();
        RedditApi redditApi = getRedditApi();
        TrackkitDao trackkitDao = getTrackkitDao();
        DateHelper dateHelper = getDateHelper();
        RedditAuthService redditAuthService = getRedditAuthService();
        FirebaseAnalytics firebaseAnalytics = getFireBaseAnalytics();
        ApplicationComponent applicationComponent = DaggerApplicationComponent
                .factory()
                .create(
                        this,
                        executor,
                        redditApi,
                        trackkitDao,
                        dateHelper,
                        redditAuthService,
                        firebaseAnalytics);

        applicationComponent.inject(this);
    }

    protected FirebaseAnalytics getFireBaseAnalytics() {
        return FirebaseAnalytics.getInstance(this);
    }

    @NotNull
    protected DateHelper getDateHelper() {
        return new DateHelper(Calendar.getInstance());
    }


    @NotNull
    protected abstract TrackkitDao getTrackkitDao();

    @NotNull
    protected abstract Executor getExecutor();

    @NonNull
    protected abstract RedditApi getRedditApi();

    @NonNull
    protected abstract RedditAuthService getRedditAuthService();

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public IdlingResource getIdlingResource() {
        return idlingResource;
    }
}
