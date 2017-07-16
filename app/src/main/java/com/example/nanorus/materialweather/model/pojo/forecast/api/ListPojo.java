
package com.example.nanorus.materialweather.model.pojo.forecast.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPojo {

    @SerializedName("dt")
    @Expose
    private double dt;
    @SerializedName("main")
    @Expose
    private MainPojo main;
    @SerializedName("weather")
    @Expose
    private java.util.List<WeatherPojo> weather = null;
    @SerializedName("clouds")
    @Expose
    private CloudsPojo clouds;
    @SerializedName("wind")
    @Expose
    private WindPojo wind;
    @SerializedName("sys")
    @Expose
    private SysPojo sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    @SerializedName("rain")
    @Expose
    private RainPojo rain;

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public MainPojo getMain() {
        return main;
    }

    public void setMain(MainPojo main) {
        this.main = main;
    }

    public java.util.List<WeatherPojo> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<WeatherPojo> weather) {
        this.weather = weather;
    }

    public CloudsPojo getClouds() {
        return clouds;
    }

    public void setClouds(CloudsPojo clouds) {
        this.clouds = clouds;
    }

    public WindPojo getWind() {
        return wind;
    }

    public void setWind(WindPojo wind) {
        this.wind = wind;
    }

    public SysPojo getSys() {
        return sys;
    }

    public void setSys(SysPojo sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public RainPojo getRain() {
        return rain;
    }

    public void setRain(RainPojo rain) {
        this.rain = rain;
    }

}
