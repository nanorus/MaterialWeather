package com.example.nanorus.materialweather.di.app;

import com.example.nanorus.materialweather.di.dayforecast.DayForecastComponent;
import com.example.nanorus.materialweather.di.dayforecast.DayForecastModule;
import com.example.nanorus.materialweather.di.settings.SettingsComponent;
import com.example.nanorus.materialweather.di.settings.SettingsModule;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewComponent;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewModule;
import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.presentation.presenter.dayforecast.DayForecastPresenter;
import com.example.nanorus.materialweather.presentation.presenter.settings.SettingsPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.DayForecastRecyclerViewAdapter;
import com.example.nanorus.materialweather.presentation.ui.adapters.ForecastRecyclerViewAdapter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.cities.CitiesAutoCompleteTextViewAdapterPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class, RouterModule.class})
@Singleton
public interface AppComponent {

    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);

    SettingsComponent plusSettingsComponent(SettingsModule settingsModule);

    DayForecastComponent plusDayForecastComponent(DayForecastModule dayForecastModule);

    AutoCompleteTextViewComponent plustAutoCompleteTextViewComponent(AutoCompleteTextViewModule settingsModule);

    void inject(CitiesAutoCompleteTextViewAdapterPresenter presenter);

    void inject(ForecastRecyclerViewAdapter forecastRecyclerViewAdapter);

    void inject(DayForecastRecyclerViewAdapter dayForecastRecyclerViewAdapter);

    void inject(DayForecastPresenter presenter);
}
