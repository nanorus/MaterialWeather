package com.example.nanorus.materialweather.view;

public interface WeatherInterface {

    interface Action {

        void setPlaceToPref(String place);

        String getPlaceFromPref();

        void loadData();

        void showData();

        void releasePresenter();

    }

    interface View {

        String getPlaceFromField();

        WeatherInterface.View getView();

    }

}
