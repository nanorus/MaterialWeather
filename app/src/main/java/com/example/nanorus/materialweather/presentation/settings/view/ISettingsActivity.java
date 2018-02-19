package com.example.nanorus.materialweather.presentation.settings.view;

import android.app.Activity;

public interface ISettingsActivity {
    Activity getView();

    void setCity(String city);

    void setEnteredCity(String enteringCity);

    void setEnteredCitySuccess(boolean success);

    void playEnteredTextFailAnimation();
}
