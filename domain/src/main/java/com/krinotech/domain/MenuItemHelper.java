package com.krinotech.domain;

public class MenuItemHelper {
    private static boolean popularSubreddits;
    private static boolean newSubreddits = true;
    private static boolean defaultSubreddits;

    public static boolean isNewOff() {
        return validate(newSubreddits, SubredditType.NEW);
    }

    public static boolean isPopularOff() {
        return validate(popularSubreddits, SubredditType.POPULAR);
    }

    public static boolean isDefaultOff() {
        return validate(defaultSubreddits, SubredditType.DEFAULT);
    }

    private static boolean validate(boolean selected, SubredditType type) {
        if (!selected) {
            setSwitches(type, true);
            return true;
        }
        return false;
    }

    public static void reset() {
        popularSubreddits = false;
        newSubreddits = false;
        defaultSubreddits = false;
    }

    private static void setSwitches(SubredditType type, boolean on) {
        switch (type) {
            case NEW:
                newSubreddits = on;
                popularSubreddits = !on;
                defaultSubreddits = !on;
                break;
            case DEFAULT:
                defaultSubreddits = on;
                newSubreddits = !on;
                popularSubreddits = !on;
                break;
            case POPULAR:
                popularSubreddits = on;
                newSubreddits = !on;
                defaultSubreddits = !on;
                break;
        }
    }
}
