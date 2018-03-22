package com.example.nanorus.materialweather.presentation.presenter.settings;

import com.example.nanorus.materialweather.presentation.view.settings.ISettingsActivity;

public interface ISettingsPresenter {
    void releasePresenter();

    void bindView(ISettingsActivity settingsActivity);

    void startWork();

    void onSaveClicked(String locality);

    void onCitiesAutoCompleteItemClicked(String selectedCity);

    void onCitiesAutoCompleteTextChanged(String text);

    void onHomeClicked();
}
