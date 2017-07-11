
package com.example.nanorus.materialweather.model.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPojo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("pubDate")
    @Expose
    private String pubDate;
    @SerializedName("condition")
    @Expose
    private ConditionPojo condition;
    @SerializedName("forecast")
    @Expose
    private List<ForecastPojo> forecast = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("guid")
    @Expose
    private GuidPojo guid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public ConditionPojo getCondition() {
        return condition;
    }

    public void setCondition(ConditionPojo condition) {
        this.condition = condition;
    }

    public List<ForecastPojo> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastPojo> forecast) {
        this.forecast = forecast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GuidPojo getGuid() {
        return guid;
    }

    public void setGuid(GuidPojo guid) {
        this.guid = guid;
    }

}
