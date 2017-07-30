
package com.example.nanorus.materialweather.model.pojo.forecast.api.five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDaysListPojo {

    @SerializedName("dt")
    @Expose
    private double dt;
    @SerializedName("main")
    @Expose
    private FiveDaysMainPojo main;
    @SerializedName("weather")
    @Expose
    private java.util.List<FiveDaysWeatherPojo> weather = null;
    @SerializedName("clouds")
    @Expose
    private FiveDaysCloudsPojo clouds;
    @SerializedName("wind")
    @Expose
    private FiveDaysWindPojo wind;
    @SerializedName("sys")
    @Expose
    private FiveDaysSysPojo sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    @SerializedName("rain")
    @Expose
    private FiveDaysRainPojo rain;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public FiveDaysMainPojo getMain() {
        return main;
    }

    public void setMain(FiveDaysMainPojo main) {
        this.main = main;
    }

    public java.util.List<FiveDaysWeatherPojo> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<FiveDaysWeatherPojo> weather) {
        this.weather = weather;
    }

    public FiveDaysCloudsPojo getClouds() {
        return clouds;
    }

    public void setClouds(FiveDaysCloudsPojo clouds) {
        this.clouds = clouds;
    }

    public FiveDaysWindPojo getWind() {
        return wind;
    }

    public void setWind(FiveDaysWindPojo wind) {
        this.wind = wind;
    }

    public FiveDaysSysPojo getSys() {
        return sys;
    }

    public void setSys(FiveDaysSysPojo sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public FiveDaysRainPojo getRain() {
        return rain;
    }

    public void setRain(FiveDaysRainPojo rain) {
        this.rain = rain;
    }

}
