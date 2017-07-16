package com.example.nanorus.materialweather.view;

import android.app.Activity;

import com.example.nanorus.materialweather.model.pojo.forecast.api.ListPojo;

public interface IWeatherActivity {

        void createWeatherList();

        void addToWeatherList(ListPojo listPojo);

        void setAdapter();

        void updateAdapter();

        void setUserEnteredPlace(String place);

        String getUserEnteredPlace();

        void setWebPlace(String place);

        IWeatherActivity getView();

        Activity getActivity();

}
