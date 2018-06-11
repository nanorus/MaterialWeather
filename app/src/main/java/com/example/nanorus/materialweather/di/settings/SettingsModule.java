package com.example.nanorus.materialweather.di.settings;

import com.example.nanorus.materialweather.model.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.presentation.presenter.settings.ISettingsPresenter;
import com.example.nanorus.materialweather.presentation.presenter.settings.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    ISettingsPresenter bindSettingsPresenter(){
        return new SettingsPresenter();
    }

}
