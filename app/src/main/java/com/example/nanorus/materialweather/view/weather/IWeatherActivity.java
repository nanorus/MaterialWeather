package com.example.nanorus.materialweather.view.weather;

import android.app.Activity;

import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;

public interface IWeatherActivity {

        void createWeatherList();

        void addToWeatherList(ShortDayWeatherPojo shortDayWeatherPojo);

        void setAdapter();

        void setNowTemperature(String temperature);

        void setNowSky(String sky);

        void updateAdapter();

        void setUserEnteredPlace(String place);

        String getUserEnteredPlace();

        void setWebPlace(String place);

        IWeatherActivity getView();

        Activity getActivity();

}
