package com.example.nanorus.materialweather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nanorus.materialweather.app.App;

public class AppPreferencesManager {
    private static SharedPreferences sPreferences;


    public static void setPlace(String place) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("place", place);
        editor.apply();
    }

    public static String getPlace() {
        return getPreferences().getString("place", "kaka");
    }

    public static SharedPreferences getPreferences() {
        if (sPreferences == null)
            sPreferences = App.getApp().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        return sPreferences;
    }

}
