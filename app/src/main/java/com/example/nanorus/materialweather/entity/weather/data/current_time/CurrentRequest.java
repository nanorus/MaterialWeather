
package com.example.nanorus.materialweather.entity.weather.data.current_time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentRequest {

    @SerializedName("coord")
    @Expose
    private CurrentCoord coord;
    @SerializedName("weather")
    @Expose
    private List<CurrentWeather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private CurrentMain main;
    @SerializedName("wind")
    @Expose
    private CurrentWind wind;
    @SerializedName("clouds")
    @Expose
    private CurrentClouds clouds;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("sys")
    @Expose
    private CurrentSys sys;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private int cod;

    public CurrentCoord getCoord() {
        return coord;
    }

    public void setCoord(CurrentCoord coord) {
        this.coord = coord;
    }

    public List<CurrentWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<CurrentWeather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public CurrentMain getMain() {
        return main;
    }

    public void setMain(CurrentMain main) {
        this.main = main;
    }

    public CurrentWind getWind() {
        return wind;
    }

    public void setWind(CurrentWind wind) {
        this.wind = wind;
    }

    public CurrentClouds getClouds() {
        return clouds;
    }

    public void setClouds(CurrentClouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public CurrentSys getSys() {
        return sys;
    }

    public void setSys(CurrentSys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

}
