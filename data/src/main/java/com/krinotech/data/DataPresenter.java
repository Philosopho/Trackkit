package com.krinotech.data;

import java.util.List;

public class DataPresenter {
    private static boolean loading;
    private static boolean networkLoading;

    public static void loading(boolean loadStatus) {
        loading = loadStatus;
    }

    public static boolean isAllFinishedLoading() {
        return isNetworkFinishedLoading() && isDatabaseFinishedLoading();
    }

    public static boolean isNetworkFinishedLoading() {
        return !networkLoading;
    }

    public static boolean isDatabaseFinishedLoading() {
        return !loading;
    }

    public static void setNetworkLoading(boolean loadStatus) { networkLoading = loadStatus; }

    public static <T> boolean isInvalid(List<T> data) {
        return isAllFinishedLoading() && data.isEmpty();
    }

    public static <T> boolean isInvalid(T data) {
        return isAllFinishedLoading() && data == null;
    }
}
