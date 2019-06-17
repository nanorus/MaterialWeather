package com.example.nanorus.materialweather.model.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.nanorus.materialweather.R;

public class ResourceManager {

    private Context context;

    public ResourceManager(Context context) {
        this.context = context;
    }

    public String enterLocalityText() {
        return context.getString(R.string.enter_locality);
    }

    public String cityNotFound() {
        return context.getString(R.string.city_not_found);
    }

    public String apiPlaceNotFound() {
        return context.getString(R.string.api_place_not_found);
    }

    public String networkError() {
        return context.getString(R.string.network_error);
    }

    public Bitmap getWeatherIconBitmap(String iconName) {
        return ((BitmapDrawable) getWeatherIconDrawable(iconName)).getBitmap();
    }

    public Bitmap getForecastIconBitmap(String iconName) {
        return ((BitmapDrawable) getForecastIconDrawable(iconName)).getBitmap();
    }


    public Drawable getForecastIconDrawable(String iconName) {
        int resource = R.drawable.ic_w_01d_forecast;
        switch (iconName) {
            case "01d":
                resource = R.drawable.ic_w_01d_forecast;
                break;
            case "01n":
                resource = R.drawable.ic_w_01n_forecast;
                break;
            case "02d":
                resource = R.drawable.ic_w_02d_forecast;
                break;
            case "02n":
                resource = R.drawable.ic_w_02n_forecast;
                break;
            case "03d":
                resource = R.drawable.ic_w_03d_forecast;
                break;
            case "03n":
                resource = R.drawable.ic_w_03n_forecast;
                break;
            case "04d":
                resource = R.drawable.ic_w_04d_forecast;
                break;
            case "04n":
                resource = R.drawable.ic_w_04n_forecast;
                break;
            case "09d":
                resource = R.drawable.ic_w_09d_forecast;
                break;
            case "09n":
                resource = R.drawable.ic_w_09n_forecast;
                break;
            case "10d":
                resource = R.drawable.ic_w_10d_forecast;
                break;
            case "10n":
                resource = R.drawable.ic_w_10n_forecast;
                break;
            case "11d":
                resource = R.drawable.ic_w_11d_forecast;
                break;
            case "11n":
                resource = R.drawable.ic_w_11n_forecast;
                break;
            case "13d":
                resource = R.drawable.ic_w_13d_forecast;
                break;
            case "13n":
                resource = R.drawable.ic_w_13n_forecast;
                break;
            case "50d":
                resource = R.drawable.ic_w_50d_forecast;
                break;
            case "50n":
                resource = R.drawable.ic_w_50n_forecast;
                break;
        }
        return context.getResources().getDrawable(resource);
    }


    public Drawable getWeatherIconDrawable(String iconName) {
        return getForecastIconDrawable(iconName);
       /* int resource = R.drawable.ic_w_01d;
        switch (iconName) {
            case "01d":
                resource = R.drawable.ic_w_01d;
                break;
            case "01n":
                resource = R.drawable.ic_w_01n;
                break;
            case "02d":
                resource = R.drawable.ic_w_02d;
                break;
            case "02n":
                resource = R.drawable.ic_w_02n;
                break;
            case "03d":
                resource = R.drawable.ic_w_03d;
                break;
            case "03n":
                resource = R.drawable.ic_w_03n;
                break;
            case "04d":
                resource = R.drawable.ic_w_04d;
                break;
            case "04n":
                resource = R.drawable.ic_w_04n;
                break;
            case "09d":
                resource = R.drawable.ic_w_09d;
                break;
            case "09n":
                resource = R.drawable.ic_w_09n;
                break;
            case "10d":
                resource = R.drawable.ic_w_10d;
                break;
            case "10n":
                resource = R.drawable.ic_w_10n;
                break;
            case "11d":
                resource = R.drawable.ic_w_11d;
                break;
            case "11n":
                resource = R.drawable.ic_w_11n;
                break;
            case "13d":
                resource = R.drawable.ic_w_13d;
                break;
            case "13n":
                resource = R.drawable.ic_w_13n;
                break;
            case "50d":
                resource = R.drawable.ic_w_50d;
                break;
            case "50n":
                resource = R.drawable.ic_w_50n;
                break;
        }
        return context.getResources().getDrawable(resource);*/
    }

}
