
package com.example.nanorus.materialweather.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindPojo {

    @SerializedName("chill")
    @Expose
    private String chill;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("speed")
    @Expose
    private String speed;

    public String getChill() {
        return chill;
    }

    public void setChill(String chill) {
        this.chill = chill;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

}
