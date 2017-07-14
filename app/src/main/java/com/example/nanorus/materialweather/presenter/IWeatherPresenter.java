package com.example.nanorus.materialweather.presenter;

public interface IWeatherPresenter {

    void loadData();

    void showData();

    void setPlaceToPref();

    String getPlaceFromPref();

    void onSearchButtonPressed();

    void releasePresenter();
}
