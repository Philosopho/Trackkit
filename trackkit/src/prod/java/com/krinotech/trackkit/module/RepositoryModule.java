package com.krinotech.trackkit.module;

import com.krinotech.data.TrackkitRepository;
import com.krinotech.data.TrackkitUserPreferences;
import com.krinotech.data.contract.ApiIdlingResource;
import com.krinotech.data.contract.Repository;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.AppThreadExecutor;
import com.krinotech.domain.ThreadExecutor;
import com.krinotech.trackkit.SubredditIdlingResource;
import com.krinotech.trackkit.dagger.module.PreferencesModule;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        PreferencesModule.class
})
public interface RepositoryModule {

    @Binds
    @Singleton
    Repository bindRepository(TrackkitRepository trackkitRepository);

    @Binds
    @Singleton
    TrackkitPreferences bindPreferences(TrackkitUserPreferences trackkitUserPreferences);

    @Binds
    @Singleton
    ThreadExecutor bindThreadExecutor(AppThreadExecutor appThreadExecutor);

    @Binds
    @Singleton
    ApiIdlingResource bindIdlingResource(SubredditIdlingResource subredditIdlingResource);


}
