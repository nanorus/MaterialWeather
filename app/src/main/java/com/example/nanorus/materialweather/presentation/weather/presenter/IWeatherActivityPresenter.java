package com.example.nanorus.materialweather.presentation.weather.presenter;

import com.example.nanorus.materialweather.presentation.weather.view.IWeatherActivity;

public interface IWeatherActivityPresenter {

    void bindView(IWeatherActivity activity);

    void startWork();

    void updateDataOnline();

    void updateDataOffline();

    void setPlaceToPref();

    void onSearchButtonPressed();

    void releasePresenter();

    void onSettingsClick();
}
