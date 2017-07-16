
package com.example.nanorus.materialweather.model.pojo.forecast.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SysPojo {

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
