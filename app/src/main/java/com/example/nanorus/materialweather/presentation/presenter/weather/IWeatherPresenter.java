package com.example.nanorus.materialweather.presentation.presenter.weather;

import com.example.nanorus.materialweather.presentation.view.weather.IWeatherActivity;

public interface IWeatherPresenter {

    void bindView(IWeatherActivity activity);

    void startPresenter();

    void releasePresenter();

    void onSettingsClick();

    void onRefresh();

    void onActivityResult(boolean isResultOk, String showingCity);
}