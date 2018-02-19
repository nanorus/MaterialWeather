package com.example.nanorus.materialweather.navigation;

import android.app.Activity;
import android.content.Intent;

import com.example.nanorus.materialweather.presentation.settings.view.SettingsActivity;

public class Router {

    public void startSettingsActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public void finishActivity(Activity activity) {
        activity.finish();
    }

    public void backPress(Activity activity) {
        activity.onBackPressed();
    }
}
