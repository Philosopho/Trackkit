package com.krinotech.trackkit.dagger.module;

import com.krinotech.trackkit.view.fragment.SubredditsFragment;
import com.krinotech.trackkit.view.fragment.TrackingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    public abstract SubredditsFragment contributeSubredditsFragment();

    @ContributesAndroidInjector
    public abstract TrackingFragment contributesTrackingFragment();
}
