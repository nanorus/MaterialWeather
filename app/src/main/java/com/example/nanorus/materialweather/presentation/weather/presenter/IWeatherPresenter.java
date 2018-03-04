package com.example.nanorus.materialweather.presentation.weather.presenter;

import com.example.nanorus.materialweather.presentation.weather.view.IWeatherActivity;

public interface IWeatherPresenter {

    void bindView(IWeatherActivity activity);

    void startWork();

    void updateDataOnline();

    void updateDataOffline();

    void releasePresenter();

    void onSettingsClick();

    void onResumeView(String showingCity);

    void onRefresh();
}
