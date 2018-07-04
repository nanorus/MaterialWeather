package com.example.nanorus.materialweather.di.settings;

import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.model.domain.settings.SettingsInteractor;
import com.example.nanorus.materialweather.model.repository.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.presenter.settings.ISettingsPresenter;
import com.example.nanorus.materialweather.presentation.presenter.settings.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

    @Provides
    ISettingsPresenter bindSettingsPresenter(ResourceManager resourceManager, AppPreferencesManager preferencesManager, WeatherRepository weatherRepository, SettingsInteractor interactor, Router router) {
        return new SettingsPresenter(resourceManager, preferencesManager, weatherRepository, interactor, router);
    }

}
