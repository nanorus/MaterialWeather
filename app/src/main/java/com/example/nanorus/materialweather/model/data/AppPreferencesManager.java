package com.example.nanorus.materialweather.model.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.nanorus.materialweather.entity.domain.weather.CurrentDayWeatherPojo;
import com.example.nanorus.materialweather.model.data.mapper.DataMapper;

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

    public String getPlace() {
        return getPreferences().getString("place", "Moscow");
    }

    public void setNowWeatherData(CurrentDayWeatherPojo currentDayWeatherPojo) {
        Log.d(TAG, "setNowWeatherData() place: " + currentDayWeatherPojo.getPlace());
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("now_place", currentDayWeatherPojo.getPlace());
        editor.putInt("now_temperature", currentDayWeatherPojo.getTemp());
        editor.putString("now_description", currentDayWeatherPojo.getDescription());
        editor.putInt("now_pressure", currentDayWeatherPojo.getPressure());
        editor.putInt("now_humidity", currentDayWeatherPojo.getHumidity());
        editor.putInt("now_cloudiness", currentDayWeatherPojo.getCloudiness());
        editor.putFloat("now_wind_speed", (float) currentDayWeatherPojo.getWindSpeed());
        editor.putInt("now_wind_direction", currentDayWeatherPojo.getWindDirection());
        editor.putString("now_icon", currentDayWeatherPojo.getIcon());
        editor.apply();
    }

    public Single<CurrentDayWeatherPojo> getNowWeatherData() {
        Log.d(TAG, "getNowWeatherData() place: " + mPreferences.getString("now_place", null));
        return Single.create(singleSubscriber -> singleSubscriber.onSuccess(new CurrentDayWeatherPojo(
                getPreferences().getInt("now_temperature", 0),
                getPreferences().getString("now_description", "null"),
                getPreferences().getInt("now_pressure", 0),
                getPreferences().getInt("now_humidity", 0),
                getPreferences().getInt("now_cloudiness", 0),
                getPreferences().getFloat("now_wind_speed", 0),
                getPreferences().getInt("now_wind_direction", 0),
                getPreferences().getString("now_place", null),
                getPreferences().getString("now_icon", "01d")
        )));
    }

    public void setLastWeatherUpdateTime(Date date) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString("last_weather_update_time", DataMapper.dateToString(date));
        editor.apply();
    }

    public String getLastWeatherUpdateTime() {
        return getPreferences().getString("last_weather_update_time", "");
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

}
