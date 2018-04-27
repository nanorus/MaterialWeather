package com.example.nanorus.materialweather.entity.weather.data;

public class TemperaturesAmplitude {

    private int minTemperature;
    private int maxTemperature;

    public TemperaturesAmplitude(int minTemperature, int maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public TemperaturesAmplitude() {
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
