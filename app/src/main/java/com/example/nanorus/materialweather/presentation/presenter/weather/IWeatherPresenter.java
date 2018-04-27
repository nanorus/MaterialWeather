package com.example.nanorus.materialweather.presentation.presenter.weather;

import com.example.nanorus.materialweather.presentation.view.weather.IWeatherActivity;

public interface IWeatherPresenter {

    void bindView(IWeatherActivity activity);

    void startWork();

    void releasePresenter();

    void onSettingsClick();

    void onResumeView(String showingCity);

    void onRefresh();
}
