package com.example.nanorus.materialweather.di.app;

import com.example.nanorus.materialweather.di.settings.SettingsComponent;
import com.example.nanorus.materialweather.di.settings.SettingsModule;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewComponent;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewModule;
import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;
import com.example.nanorus.materialweather.presentation.settings.presenter.SettingsPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.cities.CitiesAutoCompleteTextViewAdapterPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class, RouterModule.class})
@Singleton
public interface AppComponent {

    WeatherComponent plusWeatherComponent(WeatherModule weatherModule);

    SettingsComponent plusSettingsComponent(SettingsModule settingsModule);

    AutoCompleteTextViewComponent plustAutoCompleteTextViewComponent(AutoCompleteTextViewModule settingsModule);

    void inject(CitiesAutoCompleteTextViewAdapterPresenter presenter);

    void inject(SettingsPresenter settingsPresenter);
}
