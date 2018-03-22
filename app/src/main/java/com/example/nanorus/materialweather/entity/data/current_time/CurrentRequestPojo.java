
package com.example.nanorus.materialweather.entity.data.current_time;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentRequestPojo {

    @SerializedName("coord")
    @Expose
    private CurrentCoordPojo coord;
    @SerializedName("weather")
    @Expose
    private List<CurrentWeatherPojo> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private CurrentMainPojo main;
    @SerializedName("wind")
    @Expose
    private CurrentWindPojo wind;
    @SerializedName("clouds")
    @Expose
    private CurrentCloudsPojo clouds;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("sys")
    @Expose
    private CurrentSysPojo sys;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private int cod;

    public CurrentCoordPojo getCoord() {
        return coord;
    }

    public void setCoord(CurrentCoordPojo coord) {
        this.coord = coord;
    }

    public List<CurrentWeatherPojo> getWeather() {
        return weather;
    }

    public void setWeather(List<CurrentWeatherPojo> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public CurrentMainPojo getMain() {
        return main;
    }

    public void setMain(CurrentMainPojo main) {
        this.main = main;
    }

    public CurrentWindPojo getWind() {
        return wind;
    }

    public void setWind(CurrentWindPojo wind) {
        this.wind = wind;
    }

    public CurrentCloudsPojo getClouds() {
        return clouds;
    }

    public void setClouds(CurrentCloudsPojo clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public CurrentSysPojo getSys() {
        return sys;
    }

    public void setSys(CurrentSysPojo sys) {
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
