package com.krinotech.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectExecutorTest {

    @Test
    public void execute_shouldBeSynchronous() {
        final String[] strings = new String[1];
        final String expectedText = "Hello, World!";

        Runnable runnable = () -> strings[0] = expectedText;

        DirectExecutor directExecutor = new DirectExecutor();
        directExecutor.execute(runnable);

        assertEquals(expectedText, strings[0]);
    }

}