package com.example.nanorus.materialweather.presentation.view.weather;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;

public interface IWeatherActivity {

    void initForecastList();

    void updateWeatherForecast(WeatherForecast weatherForecast);

    IWeatherActivity getView();

    Activity getActivity();

    void showRefresh(boolean show);

    void setIcon(Bitmap icon);

    void closeDrawer();

    void setWeatherIcon(Bitmap icon);

    void scrollToTop();
}
