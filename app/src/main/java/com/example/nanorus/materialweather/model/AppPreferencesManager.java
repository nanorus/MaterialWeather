package com.example.nanorus.materialweather.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nanorus.materialweather.app.App;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesManager {
    private  SharedPreferences sPreferences;

    @Inject
    public  AppPreferencesManager(){

    }

    public  void setPlace(String place) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("place", place);
        editor.apply();
    }

    public  String getPlace() {
        return getPreferences().getString("place", "kaka");
    }

    public  SharedPreferences getPreferences() {
        if (sPreferences == null)
            sPreferences = App.getApp().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        return sPreferences;
    }

}
