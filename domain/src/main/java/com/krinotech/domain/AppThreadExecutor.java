package com.krinotech.domain;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppThreadExecutor implements ThreadExecutor {
    private Executor discIOExecutor;

    @Inject
    public AppThreadExecutor(Executor discIOExecutor) {
        this.discIOExecutor = discIOExecutor;
    }

    @Override
    public Executor diskIO() {
        return discIOExecutor;
    }
}
