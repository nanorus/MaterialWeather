
package com.example.nanorus.materialweather.entity.weather.data.current_time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentClouds {

    @SerializedName("all")
    @Expose
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

}
