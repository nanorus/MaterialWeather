package com.example.nanorus.materialweather.model.domain.settings;

import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.model.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.model.repository.settings.SettingsRepository;
import com.example.nanorus.materialweather.model.repository.weather.WeatherRepository;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Single;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SettingsInteractor {

    private SettingsRepository mSettingsRepository;
    private WeatherRepository mWeatherRepository;

    @Inject
    public SettingsInteractor(SettingsRepository settingsRepository, WeatherRepository weatherRepository) {
        mSettingsRepository = settingsRepository;
        mWeatherRepository = weatherRepository;
    }

    public Single<Boolean> checkCity(String city) {
        return mWeatherRepository.getRefreshedWeather(city)
                .map(currentWeather -> currentWeather != null)
                .subscribeOn(Schedulers.io());
    }

    public void setCity(String city) {
        mSettingsRepository.setCity(city);
    }

    public String getCity() {
        return mSettingsRepository.getCity();
    }

}
