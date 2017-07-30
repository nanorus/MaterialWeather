package com.example.nanorus.materialweather.presenter;

public interface IWeatherActivityPresenter {

    void loadData();

    void showData();

    void setPlaceToPref();

    String getPlaceFromPref();

    void onSearchButtonPressed();

    void releasePresenter();
}
