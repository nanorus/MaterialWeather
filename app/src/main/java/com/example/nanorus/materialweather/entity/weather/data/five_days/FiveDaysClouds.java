
package com.example.nanorus.materialweather.entity.weather.data.five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDaysClouds {

    @SerializedName("all")
    @Expose
    private long all;

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }

}
