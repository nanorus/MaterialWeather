package com.example.nanorus.materialweather.presentation.view.settings;

import android.app.Activity;

public interface ISettingsActivity {
    Activity getView();

    void setCity(String city);

    void setEnteredCity(String enteringCity);

    void showEnteredCitySuccessNotice(boolean success);

    void hideEnteredCitySuccessHotice();

    void showSaveButton(boolean show);

    void showCheckCityProgress(boolean show);
}
