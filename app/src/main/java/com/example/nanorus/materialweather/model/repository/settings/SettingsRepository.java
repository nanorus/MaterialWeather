package com.example.nanorus.materialweather.model.repository.settings;

import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.model.data.database.DatabaseManager;

import javax.inject.Inject;

import rx.Single;
import rx.schedulers.Schedulers;

public class SettingsRepository {


    private AppPreferencesManager preferencesManager;
    private CurrentTimeForecastService currentTimeForecastService;

    @Inject
    SettingsRepository(DatabaseManager databaseManager, CurrentTimeForecastService currentTimeForecastService, AppPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
        this.currentTimeForecastService = currentTimeForecastService;
    }

    public void setCity(String city) {
        preferencesManager.setCity(city);
    }

    public String getCity() {
        return preferencesManager.getCity();
    }

}
