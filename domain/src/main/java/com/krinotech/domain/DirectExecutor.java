package com.krinotech.domain;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DirectExecutor implements Executor {

    @Inject
    public DirectExecutor() {}

    public void execute(Runnable r) {
        r.run();
    }
}
