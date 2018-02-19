package com.example.nanorus.materialweather.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.nanorus.materialweather.data.entity.NowWeatherPojo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class AppPreferencesManager {
    private final String TAG = this.getClass().getSimpleName();

    private SharedPreferences mPreferences;

    @Inject
    public AppPreferencesManager(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
    }

    public void setPlace(String place) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("place", place);
        editor.apply();
    }

    public String getPlace() {
        return getPreferences().getString("place", "Moscow");
    }

    public void setNowWeatherData(NowWeatherPojo nowWeatherPojo) {
        Log.d(TAG, "setNowWeatherData() place: "+ nowWeatherPojo.getPlace());
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("now_place", nowWeatherPojo.getPlace());
        editor.putInt("now_temperature", nowWeatherPojo.getTemp());
        editor.putString("now_description", nowWeatherPojo.getDescription());
        editor.putInt("now_pressure", nowWeatherPojo.getPressure());
        editor.putInt("now_humidity", nowWeatherPojo.getHumidity());
        editor.putInt("now_cloudiness", nowWeatherPojo.getCloudiness());
        editor.putFloat("now_wind_speed", (float) nowWeatherPojo.getWindSpeed());
        editor.putInt("now_wind_direction", nowWeatherPojo.getWindDirection());
        editor.apply();
    }

    public Single<NowWeatherPojo> getNowWeatherData() {
        Log.d(TAG, "getNowWeatherData() place: "+ mPreferences.getString("now_place", null));
        return Single.create(singleSubscriber -> singleSubscriber.onSuccess(new NowWeatherPojo(
                mPreferences.getInt("now_temperature", 0),
                mPreferences.getString("now_description", "null"),
                mPreferences.getInt("now_pressure", 0),
                mPreferences.getInt("now_humidity", 0),
                mPreferences.getInt("now_cloudiness", 0),
                mPreferences.getFloat("now_wind_speed", 0),
                mPreferences.getInt("now_wind_direction", 0),
                mPreferences.getString("now_place", null)
        )));
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

}
