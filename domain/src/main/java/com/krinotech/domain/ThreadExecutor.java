package com.krinotech.domain;


import java.util.concurrent.Executor;

public interface ThreadExecutor {
    Executor diskIO();
}
