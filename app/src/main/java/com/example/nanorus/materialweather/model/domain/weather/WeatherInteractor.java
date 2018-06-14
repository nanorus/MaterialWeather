package com.example.nanorus.materialweather.model.domain.weather;

import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.repository.weather.WeatherRepository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class WeatherInteractor {

    boolean testMode = false;

    private WeatherRepository mRepository;

    @Inject
    public WeatherInteractor(WeatherRepository weatherRepository) {
        mRepository = weatherRepository;
    }

    public Single<WeatherForecast> getRefreshedWeatherForecast() {
        return Single.zip(mRepository.getRefreshedWeather(), mRepository.getRefreshedForecast(),
                (currentWeatherPojo, weekForecast) -> {
                    WeatherForecast weatherForecast = new WeatherForecast(currentWeatherPojo, weekForecast, new Date());
                    mRepository.saveWeatherForecast(weatherForecast);
                    return weatherForecast;
                }
        ).subscribeOn(Schedulers.io());
    }

    public Observable<WeatherForecast> getWeatherForecastUpdates() {
        if (!testMode)
            return Observable.interval(1000, TimeUnit.SECONDS).flatMap(aLong -> {
                if (DateUtils.hoursPassed(mRepository.getLastUpdateTime()) >= 3) {
                    return getRefreshedWeatherForecast().toObservable();
                } else {
                    return loadWeatherForecast().toObservable();
                }
            }).subscribeOn(Schedulers.io());
        else
            return Observable.interval(10, TimeUnit.SECONDS).flatMap(aLong -> {
                if (DateUtils.secondsPassed(mRepository.getLastUpdateTime()) >= 9) {
                    return getRefreshedWeatherForecast().toObservable();
                } else {
                    return loadWeatherForecast().toObservable();
                }
            }).subscribeOn(Schedulers.io());


    }


    public Single<WeatherForecast> loadWeatherForecast() {
        return Single.zip(mRepository.loadCurrentWeather(), mRepository.loadForecast(),
                (currentWeather, weekForecast) -> new WeatherForecast(currentWeather, weekForecast, mRepository.getLastUpdateTime()));
    }


}
