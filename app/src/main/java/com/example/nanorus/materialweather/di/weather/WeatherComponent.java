package com.example.nanorus.materialweather.di.weather;

import com.example.nanorus.materialweather.presentation.weather.view.WeatherActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {WeatherModule.class})
public interface WeatherComponent {

    void inject(WeatherActivity activity);

}
