package com.example.nanorus.materialweather.di.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nanorus.materialweather.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.data.api.services.FiveDaysForecastService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    Retrofit provideRetroClient() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    CurrentTimeForecastService provideCurrentTimeForecastService(Retrofit retroClient){
        return retroClient.create(CurrentTimeForecastService.class);
    }

    @Provides
    FiveDaysForecastService provideFiveDaysForecastService(Retrofit retroClient) {
        return retroClient.create(FiveDaysForecastService.class);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }
}
