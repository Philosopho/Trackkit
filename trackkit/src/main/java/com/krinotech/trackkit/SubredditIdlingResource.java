package com.krinotech.trackkit;

import androidx.annotation.Nullable;

import com.krinotech.data.contract.ApiIdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class SubredditIdlingResource implements ApiIdlingResource {
    @Nullable
    private volatile ResourceCallback resourceCallback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Inject
    public SubredditIdlingResource() { }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    @Override
    public void setIdleState(boolean isIdle) {
        isIdleNow.set(isIdle);

        if(isIdle && resourceCallback != null) {
            Timber.d("setIdleState onTransitionToIdle");
            resourceCallback.onTransitionToIdle();
        }
    }
}
