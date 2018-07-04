package com.example.nanorus.materialweather.entity.weather.repository;

import com.example.nanorus.materialweather.entity.weather.data.TemperaturesAmplitude;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DayForecast {

    private Date date;
    private TemperaturesAmplitude temperaturesAmplitude;
    private String icon;
    private List<HourForecast> hourForecastList;
    private String place;

    public DayForecast(Date date, TemperaturesAmplitude temperaturesAmplitude, String icon, List<HourForecast> hourForecastList, String place) {
        this.date = date;
        this.temperaturesAmplitude = temperaturesAmplitude;
        this.icon = icon;
        this.hourForecastList = hourForecastList;
        this.place = place;
    }

    public DayForecast() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TemperaturesAmplitude getTemperaturesAmplitude() {
        return temperaturesAmplitude;
    }

    public void setTemperaturesAmplitude(TemperaturesAmplitude temperaturesAmplitude) {
        this.temperaturesAmplitude = temperaturesAmplitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<HourForecast> getHourForecastList() {
        return hourForecastList;
    }

    public void setHourForecastList(List<HourForecast> hourForecastList) {
        this.hourForecastList = hourForecastList;
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

    private static void fillDay(DayForecast dayForecast, List<HourForecast> dayHourForecastList, Date date, List<DayForecast> dayForecasts) {
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
