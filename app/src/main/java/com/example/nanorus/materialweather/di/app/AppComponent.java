package com.example.nanorus.materialweather.di.app;

import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {

    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);

}
