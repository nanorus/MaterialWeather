
package com.example.nanorus.materialweather.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelPojo {

    @SerializedName("units")
    @Expose
    private UnitsPojo units;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("lastBuildDate")
    @Expose
    private String lastBuildDate;
    @SerializedName("ttl")
    @Expose
    private String ttl;
    @SerializedName("location")
    @Expose
    private LocationPojo location;
    @SerializedName("wind")
    @Expose
    private WindPojo wind;
    @SerializedName("atmosphere")
    @Expose
    private AtmospherePojo atmosphere;
    @SerializedName("astronomy")
    @Expose
    private AstronomyPojo astronomy;
    @SerializedName("image")
    @Expose
    private ImagePojo image;
    @SerializedName("item")
    @Expose
    private ItemPojo item;

    public UnitsPojo getUnits() {
        return units;
    }

    public void setUnits(UnitsPojo units) {
        this.units = units;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public LocationPojo getLocation() {
        return location;
    }

    public void setLocation(LocationPojo location) {
        this.location = location;
    }

    public WindPojo getWind() {
        return wind;
    }

    public void setWind(WindPojo wind) {
        this.wind = wind;
    }

    public AtmospherePojo getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(AtmospherePojo atmosphere) {
        this.atmosphere = atmosphere;
    }

    public AstronomyPojo getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(AstronomyPojo astronomy) {
        this.astronomy = astronomy;
    }

    public ImagePojo getImage() {
        return image;
    }

    public void setImage(ImagePojo image) {
        this.image = image;
    }

    public ItemPojo getItem() {
        return item;
    }

    public void setItem(ItemPojo item) {
        this.item = item;
    }

}
