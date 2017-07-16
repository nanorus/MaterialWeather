
package com.example.nanorus.materialweather.model.pojo.forecast.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloudsPojo {

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
