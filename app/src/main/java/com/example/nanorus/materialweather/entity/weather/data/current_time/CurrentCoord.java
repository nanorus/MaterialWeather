
package com.example.nanorus.materialweather.entity.weather.data.current_time;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentCoord {

    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("lat")
    @Expose
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
