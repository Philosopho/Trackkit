package com.krinotech.trackkit;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {
    @NonNull public static Set<String> trackedIds = new HashSet<String>();
    public static boolean rateLimiterHoursPassed = false;
}
