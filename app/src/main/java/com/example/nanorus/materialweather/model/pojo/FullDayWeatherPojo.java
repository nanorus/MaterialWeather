package com.example.nanorus.materialweather.model.pojo;

public class FullDayWeatherPojo {

    private int mDayOfMonth;

    private int mMonth;

    private int mSunriseHour;

    private int mSunriseMinute;

    private int mSunSetHour;

    private int mSunSetMinute;

    public FullDayWeatherPojo(int dayOfMonth, int month, int sunriseHour, int sunriseMinute, int sunSetHour, int sunSetMinute) {
        mDayOfMonth = dayOfMonth;
        mMonth = month;
        mSunriseHour = sunriseHour;
        mSunriseMinute = sunriseMinute;
        mSunSetHour = sunSetHour;
        mSunSetMinute = sunSetMinute;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getSunriseHour() {
        return mSunriseHour;
    }

    public void setSunriseHour(int sunriseHour) {
        mSunriseHour = sunriseHour;
    }

    public int getSunriseMinute() {
        return mSunriseMinute;
    }

    public void setSunriseMinute(int sunriseMinute) {
        mSunriseMinute = sunriseMinute;
    }

    public int getSunSetHour() {
        return mSunSetHour;
    }

    public void setSunSetHour(int sunSetHour) {
        mSunSetHour = sunSetHour;
    }

    public int getSunSetMinute() {
        return mSunSetMinute;
    }

    public void setSunSetMinute(int sunSetMinute) {
        mSunSetMinute = sunSetMinute;
    }
}
