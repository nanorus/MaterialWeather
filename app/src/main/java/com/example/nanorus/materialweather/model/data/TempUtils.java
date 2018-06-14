package com.example.nanorus.materialweather.model.data;

public class TempUtils {

    public static String temperatureToString(int temperature) {
        String stringTemperature = null;
        if (temperature > 0)
            stringTemperature = "+" + String.valueOf(temperature);
        else if (temperature <= 0)
            stringTemperature = String.valueOf(temperature);
        return stringTemperature;
    }

    public static int kelvinToCelsius(double kelvin) {
        return (int) Math.round(kelvin) - 273;
    }

    public static double celsiusToKelvin(int celsius) {
        return (float) celsius + 273.15F;
    }
}
