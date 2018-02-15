package com.example.nanorus.materialweather.presentation.settings.presenter;

import com.example.nanorus.materialweather.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.presentation.settings.view.ISettingsActivity;

public class SettingsPresenter implements ISettingsPresenter {
    private final String TAG = this.getClass().getSimpleName();

    private ISettingsActivity mView;
    SearchPossibleCitiesService mSearchPossibleCitiesService;

    public SettingsPresenter(SearchPossibleCitiesService searchPossibleCitiesService) {
        mSearchPossibleCitiesService = searchPossibleCitiesService;
    }

    @Override
    public void releasePresenter() {
        mView = null;
    }


    @Override
    public void bindView(ISettingsActivity settingsActivity) {
        mView = settingsActivity;
    }

    @Override
    public void startWork() {

    }

}
