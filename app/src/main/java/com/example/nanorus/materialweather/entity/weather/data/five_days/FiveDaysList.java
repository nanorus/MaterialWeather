
package com.example.nanorus.materialweather.entity.weather.data.five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDaysList {

    @SerializedName("dt")
    @Expose
    private double dt;
    @SerializedName("main")
    @Expose
    private FiveDaysMain main;
    @SerializedName("weather")
    @Expose
    private java.util.List<FiveDaysWeather> weather = null;
    @SerializedName("clouds")
    @Expose
    private FiveDaysClouds clouds;
    @SerializedName("wind")
    @Expose
    private FiveDaysWind wind;
    @SerializedName("sys")
    @Expose
    private FiveDaysSys sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    @SerializedName("rain")
    @Expose
    private FiveDaysRain rain;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public FiveDaysMain getMain() {
        return main;
    }

    public void setMain(FiveDaysMain main) {
        this.main = main;
    }

    public java.util.List<FiveDaysWeather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<FiveDaysWeather> weather) {
        this.weather = weather;
    }

    public FiveDaysClouds getClouds() {
        return clouds;
    }

    public void setClouds(FiveDaysClouds clouds) {
        this.clouds = clouds;
    }

    public FiveDaysWind getWind() {
        return wind;
    }

    public void setWind(FiveDaysWind wind) {
        this.wind = wind;
    }

    public FiveDaysSys getSys() {
        return sys;
    }

    public void setSys(FiveDaysSys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public FiveDaysRain getRain() {
        return rain;
    }

    public void setRain(FiveDaysRain rain) {
        this.rain = rain;
    }

}
