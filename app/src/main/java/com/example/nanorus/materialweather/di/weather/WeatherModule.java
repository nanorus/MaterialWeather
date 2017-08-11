package com.example.nanorus.materialweather.di.weather;

import com.example.nanorus.materialweather.model.AppPreferencesManager;
import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.presenter.IWeatherActivityPresenter;
import com.example.nanorus.materialweather.presenter.WeatherActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherModule {

    @Provides
    IWeatherActivityPresenter bindWeatherActivityPresenter(DataManager dataManager, AppPreferencesManager appPreferencesManager) {
        return new WeatherActivityPresenter(dataManager, appPreferencesManager);
    }

}
