package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.view.weather.IWeatherActivity;

public interface IWeatherActivityPresenter {

    void bindView(IWeatherActivity activity);

    void startWork();

    void updateDataOnline();

    void updateDataOffline();

    void setPlaceToPref();

    String getPlaceFromPref();

    void onSearchButtonPressed();

    void releasePresenter();
}
