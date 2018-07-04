package com.example.nanorus.materialweather.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.nanorus.materialweather.presentation.view.dayforecast.DayForecastActivity;
import com.example.nanorus.materialweather.presentation.view.settings.SettingsActivity;

import java.util.Date;

import javax.inject.Singleton;

@Singleton
public class Router {
    public static final int REQUEST_CODE_SETTINGS = 1;
    public static final int RESULT_CODE_OK = -1;
    public static final int RESULT_CODE_CANCELED = 0;

    public void startSettingsActivity(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    }

    public void startDayForecastActivity(Context context, Date date) {
        Intent intent = new Intent(context, DayForecastActivity.class);
        intent.putExtra("date", date.getTime());
        context.startActivity(intent);
    }

    public void finishActivity(Activity activity) {
        activity.finish();
    }

    public void finishActivityWithResult(Activity activity, int result) {
        activity.setResult(result);
        activity.finish();
    }

    public void backPress(Activity activity) {
        activity.onBackPressed();
    }
}
