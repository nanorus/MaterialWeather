
package com.example.nanorus.materialweather.model.pojo.forecast.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPojo {

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
    private java.util.List<ListPojo> list = null;
    @SerializedName("city")
    @Expose
    private CityPojo city;

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

    public java.util.List<ListPojo> getList() {
        return list;
    }

    public void setList(java.util.List<ListPojo> list) {
        this.list = list;
    }

    public CityPojo getCity() {
        return city;
    }

    public void setCity(CityPojo city) {
        this.city = city;
    }

}
