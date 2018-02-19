package com.example.nanorus.materialweather.presentation.settings.presenter;

import com.example.nanorus.materialweather.presentation.settings.view.ISettingsActivity;

public interface ISettingsPresenter {
    void releasePresenter();

    void bindView(ISettingsActivity settingsActivity);

    void startWork();

    void onSaveClicked(String locality);

    void onCitiesAutoCompleteItemClicked(String selectedCity);

    void onCitiesAutoCompleteTextChanged(String text);

    void onHomeClicked();
}
