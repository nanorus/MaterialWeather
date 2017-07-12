
package com.example.nanorus.materialweather.model.pojo.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityPojo {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private CoordPojo coord;
    @SerializedName("country")
    @Expose
    private String country;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordPojo getCoord() {
        return coord;
    }

    public void setCoord(CoordPojo coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
