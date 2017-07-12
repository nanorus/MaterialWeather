package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.view.WeatherInterface;

public class WeatherPresenter implements WeatherInterface.Action {
    WeatherInterface.View mView;


    public WeatherPresenter(WeatherInterface.View view) {
        mView = view;

        loadData();
        showData();
    }

    @Override
    public void setPlaceToPref(String place) {

    }

    @Override
    public String getPlaceFromPref() {
        return null;
    }

    @Override
    public void loadData() {



    }

    @Override
    public void showData() {

    }

    @Override
    public void releasePresenter() {
        mView = null;
    }
}
