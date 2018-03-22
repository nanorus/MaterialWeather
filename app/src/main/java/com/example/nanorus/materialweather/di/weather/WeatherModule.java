package com.example.nanorus.materialweather.di.weather;

import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.presenter.weather.IWeatherPresenter;
import com.example.nanorus.materialweather.presentation.presenter.weather.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    IWeatherPresenter bindWeatherActivityPresenter(WeatherRepository dataManager, AppPreferencesManager appPreferencesManager, Router router, ResourceManager resourceManager) {
        return new WeatherPresenter(dataManager, appPreferencesManager, router, resourceManager);
    }

}
