package com.codyy.urlannotation;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by kmdai on 17-12-18.
 */

public class URLManager {

    public static void init(Context context, String url, Class<?> c) {
        String className = c.getName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ClassBase.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String urlS = sharedPreferences.getString(className, url);
        reSetUrls(context, urlS, c);
    }

    public static void reSetUrls(Context context, String url, Class<?> c) {
        String className = c.getName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ClassBase.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(url)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(className, url).apply();
        }
        try {
            Class<?> clazz = Class.forName(className + ClassBase.CLASS_SUFFIX);
            Method method = clazz.getDeclaredMethod(ClassBase.METHOD_RESET_URL, String.class);
            method.invoke(clazz, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSharedUrl(Context context, String defaultUrl, Class<?> c) {
        String className = c.getName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(ClassBase.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String urlS = sharedPreferences.getString(className, defaultUrl);
        return urlS;
    }
}
