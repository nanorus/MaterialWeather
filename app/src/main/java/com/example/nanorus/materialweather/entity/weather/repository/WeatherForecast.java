package com.example.nanorus.materialweather.entity.weather.repository;

import java.util.Date;

public class WeatherForecast {

    private CurrentWeather mCurrentWeather;
    private WeekForecast mWeekForecast;
    private Date mLastUpdate;

    public WeatherForecast(CurrentWeather currentWeather, WeekForecast weekForecast, Date lastUpdate) {
        mCurrentWeather = currentWeather;
        mWeekForecast = weekForecast;
        mLastUpdate = lastUpdate;
    }

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        mCurrentWeather = currentWeather;
    }

    public WeekForecast getWeekForecast() {
        return mWeekForecast;
    }

    public void setWeekForecast(WeekForecast weekForecast) {
        mWeekForecast = weekForecast;
    }

    public Date getLastUpdate() {
        return mLastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        mLastUpdate = lastUpdate;
    }
}
