package com.krinotech.domain;

public class Filter {
    private static String[] strings = {
            "sex", "fuck", "fuc", "xxx", "nude", "nudity",
            "naked", "porn", "pron", "drugs", "weed"};

    public static boolean containsOver18Content(String string) {
        if (string != null) {
            for(String over18Content: strings) {
                if(string.toLowerCase().contains(over18Content)) {
                    return true;
                }
            }
        }
        return false;
    }
}
