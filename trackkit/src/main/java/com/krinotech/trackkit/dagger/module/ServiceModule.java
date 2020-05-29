package com.krinotech.trackkit.dagger.module;

import com.krinotech.trackkit.TrackkitWidgetService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ServiceModule {

    @ContributesAndroidInjector
    TrackkitWidgetService contributeService();
}
