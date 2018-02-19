package com.example.nanorus.materialweather.di.weather;

import com.example.nanorus.materialweather.data.AppPreferencesManager;
import com.example.nanorus.materialweather.data.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.weather.presenter.IWeatherPresenter;
import com.example.nanorus.materialweather.presentation.weather.presenter.WeatherPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    IWeatherPresenter bindWeatherActivityPresenter(WeatherRepository dataManager, AppPreferencesManager appPreferencesManager, Router router) {
        return new WeatherPresenter(dataManager, appPreferencesManager, router);
    }

}
