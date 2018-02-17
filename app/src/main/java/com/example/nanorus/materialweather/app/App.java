package com.example.nanorus.materialweather.app;

import android.app.Application;

import com.example.nanorus.materialweather.di.app.AppComponent;
import com.example.nanorus.materialweather.di.app.AppModule;
import com.example.nanorus.materialweather.di.app.DaggerAppComponent;
import com.example.nanorus.materialweather.di.settings.SettingsComponent;
import com.example.nanorus.materialweather.di.settings.SettingsModule;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewComponent;
import com.example.nanorus.materialweather.di.ui.auto_complete_text_view.AutoCompleteTextViewModule;
import com.example.nanorus.materialweather.di.weather.WeatherComponent;
import com.example.nanorus.materialweather.di.weather.WeatherModule;

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
