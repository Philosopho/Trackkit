package com.krinotech.trackkit.application;

import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.contract.TrackkitRoomDatabase;
import com.krinotech.domain.DirectExecutor;
import com.krinotech.trackkit.BaseApplication;
import com.krinotech.trackkit.doubles.RedditApiDouble;
import com.krinotech.trackkit.doubles.TrackkitDatabaseDouble;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import timber.log.Timber;

public class TrackkitApplicationMock extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void instantiateAppComponent() {
        Timber.d("instantiateAppComponent");
        super.instantiateAppComponent();
    }

    @Override
    @NotNull
    protected RedditApi getRedditApi() {
        Timber.d("getRedditApiDouble");
        return new RedditApiDouble();
    }

    @Override
    @NotNull
    protected TrackkitDao getTrackkitDao() {
        Timber.d("getTrackkitDaoDouble");
        TrackkitRoomDatabase trackkitRoomDatabase = new TrackkitDatabaseDouble();
        return trackkitRoomDatabase.trackkitDao();
    }

    @Override
    @NotNull
    protected Executor getExecutor() {
        Timber.d("getExecutorDouble");
        return new DirectExecutor();
    }


}
