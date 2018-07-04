package com.example.nanorus.materialweather.app;

import android.app.Application;

import com.example.nanorus.materialweather.di.app.AppComponent;
import com.example.nanorus.materialweather.di.app.AppModule;
import com.example.nanorus.materialweather.di.app.DaggerAppComponent;
import com.example.nanorus.materialweather.di.dayforecast.DayForecastComponent;
import com.example.nanorus.materialweather.di.dayforecast.DayForecastModule;
import com.example.nanorus.materialweather.di.settings.SettingsComponent;
import com.example.nanorus.materialweather.di.settings.SettingsModule;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewComponent;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewModule;
import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;

public class App extends Application {

    private static App sInstance;

    public App() {
        sInstance = this;
    }

    public static App getApp() {
        return sInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        sInstance = null;
        super.finalize();
    }

    AppComponent mAppComponent;

    WeatherComponent mWeatherComponent;
    SettingsComponent mSettingsComponent;
    DayForecastComponent dayForecastComponent;
    AutoCompleteTextViewComponent mAutoCompleteTextViewComponent;

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getApp().getApplicationContext()))
                    .build();
        }
        return mAppComponent;
    }

    public void releaseWeatherComponent() {
        mWeatherComponent = null;
    }

    public WeatherComponent getWeatherComponent() {
        if (mWeatherComponent == null) {
            mWeatherComponent = getAppComponent().plusWeatherComponent(new WeatherModule());
        }
        return mWeatherComponent;
    }

    public DayForecastComponent getDayForecastComponent() {
        if (dayForecastComponent==null)
            dayForecastComponent = getAppComponent().plusDayForecastComponent(new DayForecastModule());
        return dayForecastComponent;
    }

    public AutoCompleteTextViewComponent getAutoCompleteTextViewComponent() {
        if (mAutoCompleteTextViewComponent == null) {
            mAutoCompleteTextViewComponent = getAppComponent().plustAutoCompleteTextViewComponent(new AutoCompleteTextViewModule());
        }
        return mAutoCompleteTextViewComponent;
    }

    public void releaseAutoCompleteTextViewComponent() {
        mAutoCompleteTextViewComponent = null;
    }

    public SettingsComponent getSettingsComponent() {
        if (mSettingsComponent == null)
            mSettingsComponent = getAppComponent().plusSettingsComponent(new SettingsModule());
        return mSettingsComponent;
    }

}
