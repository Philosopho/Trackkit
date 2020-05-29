package com.krinotech.trackkit.dagger.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.krinotech.data.contract.TrackkitPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class PreferencesModule {

    @Provides
    public static SharedPreferences provideSharedPreferences(Application context) {
        return context.getSharedPreferences(TrackkitPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
