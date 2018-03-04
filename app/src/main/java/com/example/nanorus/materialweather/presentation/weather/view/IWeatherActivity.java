package com.example.nanorus.materialweather.presentation.weather.view;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;

import java.util.List;

public interface IWeatherActivity {

    void createWeatherList(List<ShortDayWeatherPojo> weatherDaysList);

    void setAdapter();

    void setNowTemperature(String temperature);

    void setNowSky(String sky);

    void updateAdapter();

    void setWebPlace(String place);

    IWeatherActivity getView();

    Activity getActivity();

    void showRefresh(boolean show);

    void closeDrawer();

    void setLastWeatherUpdateTime(String time);

    void setWeatherIcon(Bitmap icon);
}
