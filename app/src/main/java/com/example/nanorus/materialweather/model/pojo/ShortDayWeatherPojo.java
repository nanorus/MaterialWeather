package com.example.nanorus.materialweather.model.pojo;

public class ShortDayWeatherPojo {

    private int mDayOfMonth;

    private int mMonth;

    private int mDayOfWeek;

    private int mMinTemp;

    private int mMaxTemp;

    public ShortDayWeatherPojo(int dayOfMonth, int month, int dayOfWeek, int minTemp, int maxTemp) {
        mDayOfMonth = dayOfMonth;
        mMonth = month;
        mDayOfWeek = dayOfWeek;
        mMinTemp = minTemp;
        mMaxTemp = maxTemp;
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

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public int getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(int minTemp) {
        mMinTemp = minTemp;
    }

    public int getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        mMaxTemp = maxTemp;
    }
}
