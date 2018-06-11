package com.example.nanorus.materialweather.entity.weather.repository;

import com.example.nanorus.materialweather.entity.weather.data.current_time.CurrentRequest;
import com.example.nanorus.materialweather.model.data.TempUtils;

import java.util.Locale;

public class CurrentWeather {

    private int mTemp;
    private String mDescription;
    private int mPressure;
    private int mHumidity;
    private int mCloudiness;
    private double mWindSpeed;
    private int mWindDirection;
    private String mPlace;
    private String mCity;
    private String mIcon;

    public CurrentWeather(int temp, String description, int pressure, int humidity, int cloudiness, double windSpeed, int windDirection,String city, String place,  String icon) {
        mTemp = temp;
        mDescription = description;
        mPressure = pressure;
        mHumidity = humidity;
        mCloudiness = cloudiness;
        mWindSpeed = windSpeed;
        mWindDirection = windDirection;
        mPlace = place;
        mCity = city;
        mIcon = icon;
    }

    public String getCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
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

    public static CurrentWeather map(CurrentRequest currentRequest){
        return new CurrentWeather(
                TempUtils.kelvinToCelsius(currentRequest.getMain().getTemp()),
                currentRequest.getWeather().get(0).getDescription(),
                (int) currentRequest.getMain().getPressure(),
                currentRequest.getMain().getHumidity(),
                currentRequest.getClouds().getAll(),
                currentRequest.getWind().getSpeed(),
                currentRequest.getCod(),
                currentRequest.getName(),
                currentRequest.getName() + ", " +
                        (new Locale("en-US", currentRequest.getSys().getCountry())).getDisplayCountry(),
                currentRequest.getWeather().get(0).getIcon()
        );
    }
}
