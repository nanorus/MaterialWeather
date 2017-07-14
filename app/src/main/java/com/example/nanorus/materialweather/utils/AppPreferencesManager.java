package com.example.nanorus.materialweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesManager {
    Context mContext;
    SharedPreferences mPreferences;

    public AppPreferencesManager(Context context) {
        mContext = context;
        mPreferences = mContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }

    public void setPlace(String place) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("place", place);
        editor.apply();
    }

    public String getPlace() {
        return mPreferences.getString("place", "kaka");
    }

}
