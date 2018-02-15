package com.example.nanorus.materialweather.di.app;

import com.example.nanorus.materialweather.di.settings.SettingsComponent;
import com.example.nanorus.materialweather.di.settings.SettingsModule;
import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;
import com.example.nanorus.materialweather.presentation.ui.adapters.CitiesAutoCompleteAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class, RouterModule.class})
@Singleton
public interface AppComponent {

    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);
    SettingsComponent plusSettingsComponent(SettingsModule settingsModule);

    void inject(CitiesAutoCompleteAdapter citiesAutoCompleteAdapter);
}
