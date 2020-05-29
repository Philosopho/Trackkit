package com.krinotech.trackkit.module;

import com.krinotech.data.TrackkitRepository;
import com.krinotech.data.contract.ApiIdlingResource;
import com.krinotech.data.contract.Repository;
import com.krinotech.data.contract.TrackkitPreferences;
import com.krinotech.domain.AppThreadExecutor;
import com.krinotech.domain.ThreadExecutor;
import com.krinotech.trackkit.SubredditIdlingResource;
import com.krinotech.trackkit.dagger.module.PreferencesModule;
import com.krinotech.trackkit.doubles.TrackkitUserPreferencesDouble;

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
    TrackkitPreferences bindPreferences(TrackkitUserPreferencesDouble trackkitUserPreferences);

    @Binds
    @Singleton
    ThreadExecutor bindThreadExecutor(AppThreadExecutor appThreadExecutor);

    @Binds
    @Singleton
    ApiIdlingResource bindIdlingResource(SubredditIdlingResource subredditIdlingResource);

}
