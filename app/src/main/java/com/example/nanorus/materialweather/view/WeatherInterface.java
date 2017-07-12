package com.example.nanorus.materialweather.view;

import android.app.Activity;

import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;

public interface WeatherInterface {

    interface Action {

        void loadData();

        void showData();

        void setPlaceToPref();

        String getPlaceFromPref();

        void onSearchButtonPressed();

        void releasePresenter();

    }

    interface View {

        void createWeatherList();

        void addToWeatherList(ListPojo listPojo);

        void setAdapter();

        void updateAdapter();

        void setUserEnteredPlace(String place);

        String getUserEnteredPlace();

        void setWebPlace(String place);

        WeatherInterface.View getView();

        Activity getActivity();
    }

}
