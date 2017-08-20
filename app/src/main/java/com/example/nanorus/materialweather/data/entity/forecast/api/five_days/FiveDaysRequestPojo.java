
package com.example.nanorus.materialweather.data.entity.forecast.api.five_days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDaysRequestPojo {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private long cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<FiveDaysListPojo> list = null;
    @SerializedName("city")
    @Expose
    private FiveDaysCityPojo city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public java.util.List<FiveDaysListPojo> getList() {
        return list;
    }

    public void setList(java.util.List<FiveDaysListPojo> list) {
        this.list = list;
    }

    public FiveDaysCityPojo getCity() {
        return city;
    }

    public void setCity(FiveDaysCityPojo city) {
        this.city = city;
    }

}
