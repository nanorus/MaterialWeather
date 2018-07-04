package com.example.nanorus.materialweather.di.dayforecast;

import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.domain.dayforecast.DayForecastInteractor;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.presenter.dayforecast.DayForecastPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DayForecastModule {

    @Provides
    DayForecastPresenter bindDayForecastPresenter(DayForecastInteractor interactor, ResourceManager resourceManager, Router router) {
        return new DayForecastPresenter(interactor, resourceManager, router);
    }
}
