package com.mnayef.library.utils;

import android.widget.TextView;

/**
 * Created by Mohamed Hamdan on 2017-Jun-02.
 * mohamed.nayef95@gmail.com
 */
public class Utils {

    /**
     * This method for get cache key from url by ([^a-z0-9_-]+ regex).
     */
    public static String getCacheKey(String url) {
        StringBuilder stringBuilder = new StringBuilder(url.replaceAll("[^a-z0-9_-]+", ""));
        stringBuilder.setLength(stringBuilder.length() > 60 ? 60 : stringBuilder.length());
        return stringBuilder.toString();
    }

    /**
     * This method return @{@link TextView} chars fit by view width
     *
     * @param str  String you want set.
     * @param view @{@link TextView} you want set fit text.
     * @return Characters length.
     */
    public static int getTvFitChars(String str, TextView view) {
        return view.getPaint().breakText(str, 0, str.length(), true, view.getWidth(), null);
    }
}
