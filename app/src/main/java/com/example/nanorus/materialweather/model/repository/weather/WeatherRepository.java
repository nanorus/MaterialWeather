package com.example.nanorus.materialweather.model.repository.weather;

import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.entity.weather.repository.HourForecast;
import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;
import com.example.nanorus.materialweather.entity.weather.repository.WeekForecast;
import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.model.data.api.services.FiveDaysForecastService;
import com.example.nanorus.materialweather.model.data.database.DatabaseManager;

import java.util.Date;

import javax.inject.Inject;

import rx.Single;

public class WeatherRepository {

    private DatabaseManager mDatabaseManager;
    private CurrentTimeForecastService mCurrentTimeForecastService;
    private FiveDaysForecastService mFiveDaysForecastService;
    private AppPreferencesManager mAppPreferencesManager;

    @Inject
    public WeatherRepository(CurrentTimeForecastService currentTimeForecastService,
                             FiveDaysForecastService fiveDaysForecastService, DatabaseManager databaseManager,
                             AppPreferencesManager appPreferencesManager) {
        mCurrentTimeForecastService = currentTimeForecastService;
        mFiveDaysForecastService = fiveDaysForecastService;
        mDatabaseManager = databaseManager;
        mAppPreferencesManager = appPreferencesManager;
    }

    public Single<WeekForecast> getRefreshedForecast() {
        return mFiveDaysForecastService.getRequest(mAppPreferencesManager.getPlace())
                .map(fiveDaysRequest -> HourForecast.map(fiveDaysRequest.getList()))
                .map(DayForecast::fromHourForecasts)
                .map(WeekForecast::new);

    }

    public Single<CurrentWeather> getRefreshedWeather() {
        return mCurrentTimeForecastService.getRequest(mAppPreferencesManager.getPlace())
                .map(CurrentWeather::map);
    }

    public Single<CurrentWeather> getRefreshedWeather(String city) {
        return mCurrentTimeForecastService.getRequest(city)
                .map(CurrentWeather::map);
    }

    public void saveWeatherForecast(WeatherForecast weatherForecast) {
        mAppPreferencesManager.setNowWeatherData(weatherForecast.getCurrentWeather());
        setLastUpdateTime(weatherForecast.getLastUpdate());
        mDatabaseManager.putWeekForecast(weatherForecast.getWeekForecast());
    }

    public Single<CurrentWeather> loadCurrentWeather (){
        return mAppPreferencesManager.getNowWeatherData();
    }

    public Single<WeekForecast> loadForecast (){
        return mDatabaseManager.getWeekForecast();
    }

    public Date getLastUpdateTime() {
        return mAppPreferencesManager.getLastWeatherUpdateTime();
    }

    private void setLastUpdateTime(Date date) {
        mAppPreferencesManager.setLastWeatherUpdateTime(date);
    }

}
