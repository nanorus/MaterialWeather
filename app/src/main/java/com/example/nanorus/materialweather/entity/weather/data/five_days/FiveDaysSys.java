
package com.example.nanorus.materialweather.entity.weather.data.five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDaysSys {

    @SerializedName("pod")
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
