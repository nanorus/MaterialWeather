package com.example.nanorus.materialweather.entity.weather.repository;

import com.example.nanorus.materialweather.entity.weather.data.five_days.FiveDaysList;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.data.TempUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HourForecast {

    private int mTemp;
    private String mDescription;
    private int mPressure;
    private int mHumidity;
    private int mCloudiness;
    private double mWindSpeed;
    private int mWindDirection;
    private Date mDate;
    private String mIcon;

    public HourForecast(int temp, String description, int pressure, int humidity, int cloudiness, double windSpeed, int windDirection, Date date, String icon) {
        mTemp = temp;
        mDescription = description;
        mPressure = pressure;
        mHumidity = humidity;
        mCloudiness = cloudiness;
        mWindSpeed = windSpeed;
        mWindDirection = windDirection;
        mDate = date;
        mIcon = icon;
    }

    public HourForecast() {
    }

    public int getTemp() {
        return mTemp;
    }

    public void setTemp(int temp) {
        mTemp = temp;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getPressure() {
        return mPressure;
    }

    public void setPressure(int pressure) {
        mPressure = pressure;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public void setHumidity(int humidity) {
        mHumidity = humidity;
    }

    public int getCloudiness() {
        return mCloudiness;
    }

    public void setCloudiness(int cloudiness) {
        mCloudiness = cloudiness;
    }

    public double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        mWindSpeed = windSpeed;
    }

    public int getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(int windDirection) {
        mWindDirection = windDirection;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public static HourForecast map(FiveDaysList fiveDaysList) {
        return new HourForecast(
                TempUtils.kelvinToCelsius(fiveDaysList.getMain().getTemp()),
                fiveDaysList.getWeather().get(0).getDescription(),
                (int) fiveDaysList.getMain().getPressure(),
                (int) fiveDaysList.getMain().getHumidity(),
                (int) fiveDaysList.getClouds().getAll(),
                fiveDaysList.getWind().getSpeed(),
                (int) fiveDaysList.getWind().getDeg(),
                DateUtils.dtTxtToDate(fiveDaysList.getDtTxt()),
                fiveDaysList.getWeather().get(0).getIcon()
        );
    }

    public static List<HourForecast> map(List<FiveDaysList> fiveDaysLists) {
        List<HourForecast> forecastList = new ArrayList<>();
        for (FiveDaysList fiveDaysList : fiveDaysLists) {
            forecastList.add(map(fiveDaysList));
        }
        return forecastList;
    }
}
