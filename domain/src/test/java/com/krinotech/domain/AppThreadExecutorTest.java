package com.krinotech.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class AppThreadExecutorTest {
    private AppThreadExecutor testSubject;

    private Executor expectedDiskIO;

    @Before
    public void setUp() {
        expectedDiskIO = Executors.newCachedThreadPool();

        testSubject = new AppThreadExecutor(expectedDiskIO);
    }

    @Test
    public void diskIO() {
        assertEquals(expectedDiskIO, testSubject.diskIO());
    }
}