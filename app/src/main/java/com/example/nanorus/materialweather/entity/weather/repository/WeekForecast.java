package com.example.nanorus.materialweather.entity.weather.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekForecast {

    private List<DayForecast> dayForecastList;

    public WeekForecast(List<DayForecast> dayForecastList) {
        this.dayForecastList = dayForecastList;
    }

    public WeekForecast() {
    }

    public List<DayForecast> getDayForecastList() {
        return dayForecastList;
    }

    public void setDayForecastList(List<DayForecast> dayForecastList) {
        this.dayForecastList = dayForecastList;
    }

    public List<HourForecast> getHourForecasts() {
        List<HourForecast> hourForecasts = new ArrayList<>();
        for (DayForecast dayForecast : dayForecastList) {
            hourForecasts.addAll(dayForecast.getHourForecastList());
        }
        return hourForecasts;
    }

    public DayForecast getDayForecast(Date date) {
        for (DayForecast dayForecast : dayForecastList) {
            if (compareDays(dayForecast.getDate(), date)) {
                return dayForecast;
            }
        }
        return dayForecastList.get(0);
    }

    private boolean compareDays(Date one, Date two) {
        Calendar calOne = Calendar.getInstance();
        calOne.setTime(one);
        int dayOne = calOne.get(Calendar.DAY_OF_YEAR);

        Calendar calTwo = Calendar.getInstance();
        calOne.setTime(two);
        int dayTwo = calOne.get(Calendar.DAY_OF_YEAR);

        return dayOne == dayTwo;
    }

}
