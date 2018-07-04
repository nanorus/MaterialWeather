package com.example.nanorus.materialweather.model.domain.dayforecast;

import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.repository.weather.WeatherRepository;

import java.util.Date;

import javax.inject.Inject;

import rx.Single;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DayForecastInteractor {

    WeatherRepository weatherRepository;

    @Inject
    public DayForecastInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    public String getPlace() {
        return weatherRepository.getSavedCity();
    }

    public String getLastUpdate() {
        return DateUtils.dateToString(weatherRepository.getLastUpdateTime());
    }

    public Single<DayForecast> getRefreshedDayForecast(Date date) {
        return Single.zip(weatherRepository.getRefreshedWeather(), weatherRepository.getRefreshedForecast(),
                (currentWeatherPojo, weekForecast) -> {
                    WeatherForecast weatherForecast = new WeatherForecast(currentWeatherPojo, weekForecast, new Date());
                    weatherRepository.saveWeatherForecast(weatherForecast);
                    return weatherForecast;
                }
        ).map(weatherForecast -> weatherForecast.getWeekForecast().getDayForecast(date))
               .subscribeOn(Schedulers.io());
    }

    public Single<DayForecast> getDayForecast(Date date) {
        return weatherRepository.loadForecast().map(weekForecast -> weekForecast.getDayForecast(date))
                .subscribeOn(Schedulers.io());
    }
}
