package com.example.nanorus.materialweather.entity.weather.repository;

import java.util.Date;

public class WeatherForecast {

    private CurrentWeather currentWeather;
    private WeekForecast weekForecast;
    private Date lastUpdate;

    public WeatherForecast(CurrentWeather currentWeather, WeekForecast weekForecast, Date lastUpdate) {
        this.currentWeather = currentWeather;
        this.weekForecast = weekForecast;
        this.lastUpdate = lastUpdate;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public WeekForecast getWeekForecast() {
        return weekForecast;
    }

    public void setWeekForecast(WeekForecast weekForecast) {
        this.weekForecast = weekForecast;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
