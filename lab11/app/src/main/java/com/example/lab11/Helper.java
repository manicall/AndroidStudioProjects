package com.example.lab11;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public final class Helper {
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_PATH = "path";
    private static SharedPreferences mSettings;

    public static void setSettings(Context context, String value){
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_PATH, value);
        editor.apply();
    }
    public static String getSettings(Context context){
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return mSettings.getString(APP_PREFERENCES_PATH,
                Environment.getExternalStorageDirectory().toString());
    }
}