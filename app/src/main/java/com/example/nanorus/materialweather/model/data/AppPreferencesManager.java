package com.example.nanorus.materialweather.model.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;

import java.util.Date;

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
    public void setCity(String city) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("city", city);
        editor.apply();
    }

    public String getPlace() {
        return getPreferences().getString("place", "Moscow");
    }
    public String getCity() {
        return getPreferences().getString("city", "Moscow");
    }

    public void setNowWeatherData(CurrentWeather currentWeather) {
        Log.d(TAG, "setNowWeatherData() place: " + currentWeather.getPlace());
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("now_city", currentWeather.getCity());
        editor.putString("now_place", currentWeather.getPlace());
        editor.putInt("now_temperature", currentWeather.getTemp());
        editor.putString("now_description", currentWeather.getDescription());
        editor.putInt("now_pressure", currentWeather.getPressure());
        editor.putInt("now_humidity", currentWeather.getHumidity());
        editor.putInt("now_cloudiness", currentWeather.getCloudiness());
        editor.putFloat("now_wind_speed", (float) currentWeather.getWindSpeed());
        editor.putInt("now_wind_direction", currentWeather.getWindDirection());
        editor.putString("now_icon", currentWeather.getIcon());
        editor.apply();
    }

    public Single<CurrentWeather> getNowWeatherData() {
        Log.d(TAG, "getNowWeatherData() place: " + mPreferences.getString("now_place", null));
        return Single.create(singleSubscriber -> singleSubscriber.onSuccess(
                new CurrentWeather(
                        getPreferences().getInt("now_temperature", 0),
                        getPreferences().getString("now_description", "null"),
                        getPreferences().getInt("now_pressure", 0),
                        getPreferences().getInt("now_humidity", 0),
                        getPreferences().getInt("now_cloudiness", 0),
                        getPreferences().getFloat("now_wind_speed", 0),
                        getPreferences().getInt("now_wind_direction", 0),
                        getPreferences().getString("now_city", null),
                        getPreferences().getString("now_place", null),
                        getPreferences().getString("now_icon", "01d")
        )));
    }

    public void setLastWeatherUpdateTime(Date date) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("last_weather_update_time", DateUtils.dateToDtTxt(date));
        editor.apply();
    }

    public Date getLastWeatherUpdateTime() {
        return DateUtils.dtTxtToDate(getPreferences().getString("last_weather_update_time", ""));
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

}
