package com.example.nanorus.materialweather.entity.weather.repository;

import com.example.nanorus.materialweather.entity.weather.data.TemperaturesAmplitude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DayForecast {

    private Date mDate;
    private TemperaturesAmplitude mTemperaturesAmplitude;
    private String mIcon;
    private List<HourForecast> mHourForecastList;

    public DayForecast(Date date, TemperaturesAmplitude temperaturesAmplitude, String icon, List<HourForecast> hourForecastList) {
        mDate = date;
        mTemperaturesAmplitude = temperaturesAmplitude;
        mIcon = icon;
        mHourForecastList = hourForecastList;
    }

    public DayForecast() {
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public TemperaturesAmplitude getTemperaturesAmplitude() {
        return mTemperaturesAmplitude;
    }

    public void setTemperaturesAmplitude(TemperaturesAmplitude temperaturesAmplitude) {
        mTemperaturesAmplitude = temperaturesAmplitude;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public List<HourForecast> getHourForecastList() {
        return mHourForecastList;
    }

    public void setHourForecastList(List<HourForecast> hourForecastList) {
        mHourForecastList = hourForecastList;
    }

    public static List<DayForecast> fromHourForecasts(List<HourForecast> hourForecasts) {
        List<DayForecast> dayForecasts = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(hourForecasts.get(0).getDate());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DayForecast dayForecast = new DayForecast();
        List<HourForecast> dayHourForecastList = new ArrayList<>();

        for (HourForecast hourForecast : hourForecasts) {
            cal.setTime(hourForecast.getDate());
            if (day == cal.get(Calendar.DAY_OF_MONTH)) {
                // add hour forecast to same day
                dayHourForecastList.add(hourForecast);
            } else {
                // fill in the same day and create new day
                fillDay(dayForecast, dayHourForecastList, oneDayEarlier(cal), dayForecasts);
                dayHourForecastList = new ArrayList<>();
                day = cal.get(Calendar.DAY_OF_MONTH);
                dayForecast = new DayForecast();
            }
        }
        fillDay(dayForecast, dayHourForecastList, cal.getTime(), dayForecasts);
        return dayForecasts;
    }

    private static void fillDay(DayForecast dayForecast, List<HourForecast> dayHourForecastList, Date date, List<DayForecast> dayForecasts){
        dayForecast.setTemperaturesAmplitude(getTemperaturesAmplitude(dayHourForecastList));
        dayForecast.setHourForecastList(dayHourForecastList);
        dayForecast.setIcon(dayHourForecastList.get((dayHourForecastList.size()) / 2).getIcon());
        dayForecast.setDate(date);
        dayForecasts.add(dayForecast);
    }

    public static Date oneDayEarlier(Calendar calendar) {
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        return date;
    }

    public static TemperaturesAmplitude getTemperaturesAmplitude(List<HourForecast> hourForecasts) {
        List<Integer> temperatures = new ArrayList<>();
        for (HourForecast hourForecast : hourForecasts) {
            temperatures.add(hourForecast.getTemp());
        }
        return new TemperaturesAmplitude(
                Collections.min(temperatures),
                Collections.max(temperatures)
        );
    }

}
