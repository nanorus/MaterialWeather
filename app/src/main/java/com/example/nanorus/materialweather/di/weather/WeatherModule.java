package com.example.nanorus.materialweather.di.weather;

import com.example.nanorus.materialweather.data.AppPreferencesManager;
import com.example.nanorus.materialweather.data.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.weather.presenter.IWeatherActivityPresenter;
import com.example.nanorus.materialweather.presentation.weather.presenter.WeatherActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    IWeatherActivityPresenter bindWeatherActivityPresenter(WeatherRepository dataManager, AppPreferencesManager appPreferencesManager, Router router) {
        return new WeatherActivityPresenter(dataManager, appPreferencesManager, router);
    }

}
