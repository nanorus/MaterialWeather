package com.example.nanorus.materialweather.entity.domain.weather;

import com.example.nanorus.materialweather.entity.data.current_time.CurrentRequestPojo;
import com.example.nanorus.materialweather.model.data.mapper.DataMapper;

import java.util.Locale;

public class CurrentDayWeatherPojo {

    private int mTemp;

    private String mDescription;

    private int mPressure;

    private int mHumidity;

    private int mCloudiness;

    private double mWindSpeed;

    private int mWindDirection;

    private String mPlace;

    private String mIcon;

    public CurrentDayWeatherPojo(int temp, String description, int pressure, int humidity, int cloudiness, double windSpeed, int windDirection, String place, String icon) {
        mTemp = temp;
        mDescription = description;
        mPressure = pressure;
        mHumidity = humidity;
        mCloudiness = cloudiness;
        mWindSpeed = windSpeed;
        mWindDirection = windDirection;
        mPlace = place;
        mIcon = icon;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
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

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public static CurrentDayWeatherPojo map(CurrentRequestPojo currentRequestPojo){
        return new CurrentDayWeatherPojo(
                DataMapper.kelvinToCelsius(currentRequestPojo.getMain().getTemp()),
                currentRequestPojo.getWeather().get(0).getDescription(),
                (int) currentRequestPojo.getMain().getPressure(),
                currentRequestPojo.getMain().getHumidity(),
                currentRequestPojo.getClouds().getAll(),
                currentRequestPojo.getWind().getSpeed(),
                currentRequestPojo.getCod(),
                currentRequestPojo.getName() + ", " +
                        (new Locale("en", currentRequestPojo.getSys().getCountry())).getDisplayCountry(),
                currentRequestPojo.getWeather().get(0).getIcon()
        );
    }
}
