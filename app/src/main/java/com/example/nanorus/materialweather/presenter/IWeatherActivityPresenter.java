package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.view.weather.IWeatherActivity;

public interface IWeatherActivityPresenter {

    void bindView(IWeatherActivity activity);

    void startWork();

    void loadData();

    void showData();

    void setPlaceToPref();

    String getPlaceFromPref();

    void onSearchButtonPressed();

    void releasePresenter();
}
