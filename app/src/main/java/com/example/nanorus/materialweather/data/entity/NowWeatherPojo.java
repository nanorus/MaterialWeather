package com.example.nanorus.materialweather.data.entity;

public class NowWeatherPojo {

    private int mTemp;

    private String mDescription;

    private int mPressure;

    private int mHumidity;

    private int mCloudiness;

    private double mWindSpeed;

    private int mWindDirection;

    private String mPlace;

    public NowWeatherPojo(int temp, String description, int pressure, int humidity, int cloudiness, double windSpeed, int windDirection, String place) {
        mTemp = temp;
        mDescription = description;
        mPressure = pressure;
        mHumidity = humidity;
        mCloudiness = cloudiness;
        mWindSpeed = windSpeed;
        mWindDirection = windDirection;
        mPlace = place;
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
}
