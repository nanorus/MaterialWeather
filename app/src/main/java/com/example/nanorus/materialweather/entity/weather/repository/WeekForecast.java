package com.example.nanorus.materialweather.entity.weather.repository;

import java.util.ArrayList;
import java.util.List;

public class WeekForecast {

    private List<DayForecast> mDayForecastList;

    public WeekForecast(List<DayForecast> dayForecastList) {
        mDayForecastList = dayForecastList;
    }

    public WeekForecast() {
    }

    public List<DayForecast> getDayForecastList() {
        return mDayForecastList;
    }

    public void setDayForecastList(List<DayForecast> dayForecastList) {
        mDayForecastList = dayForecastList;
    }

    public List<HourForecast> getHourForecasts() {
        List<HourForecast> hourForecasts = new ArrayList<>();
        for (DayForecast dayForecast : mDayForecastList) {
            hourForecasts.addAll(dayForecast.getHourForecastList());
        }
        return hourForecasts;
    }

}
