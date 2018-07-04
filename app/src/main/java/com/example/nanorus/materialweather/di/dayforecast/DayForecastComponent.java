package com.example.nanorus.materialweather.di.dayforecast;

import com.example.nanorus.materialweather.presentation.view.dayforecast.DayForecastActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {DayForecastModule.class})
public interface DayForecastComponent {

   void inject(DayForecastActivity activity);

}
