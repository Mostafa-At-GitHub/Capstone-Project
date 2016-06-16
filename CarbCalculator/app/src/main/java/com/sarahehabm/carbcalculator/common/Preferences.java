package com.sarahehabm.carbcalculator.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 Created by Sarah E. Mostafa on 09-Jun-16.
 */

public class Preferences {
    private final static String PREFERENCES_NAME = "CarbCalculatorPreferences";
    public final static String KEY_NAME = "name";
    public final static String KEY_IMAGE_URL = "image_url";
    public final static String KEY_BIRTHDAY = "birthday";

    private Context context;
    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        this.context = context;
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    public boolean putString(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean putLong(String key, long value) {
        return sharedPreferences.edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }
}
