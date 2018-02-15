package com.example.nanorus.materialweather.di.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nanorus.materialweather.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.data.api.services.FiveDaysForecastService;
import com.example.nanorus.materialweather.data.api.services.IpLocationService;
import com.example.nanorus.materialweather.data.api.services.SearchPossibleCitiesService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    OkHttpClient provideLoggerOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    @Provides
    @Singleton
    @Named("WeatherRetroClient")
    Retrofit provideWeatherRetroClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("CitiesRetroClient")
    Retrofit providePossibleCitiesRetroClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("IpLocationRetroClient")
    Retrofit provideIpLocationRetroClient(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://ip-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    SearchPossibleCitiesService provideSearchPossibleCitiesService(@Named("CitiesRetroClient") Retrofit retroClient) {
        return retroClient.create(SearchPossibleCitiesService.class);
    }

    @Provides
    IpLocationService provideIpLocationService(@Named("IpLocationRetroClient") Retrofit retroClient) {
        return retroClient.create(IpLocationService.class);
    }

    @Provides
    CurrentTimeForecastService provideCurrentTimeForecastService(@Named("WeatherRetroClient") Retrofit retroClient) {
        return retroClient.create(CurrentTimeForecastService.class);
    }

    @Provides
    FiveDaysForecastService provideFiveDaysForecastService(@Named("WeatherRetroClient") Retrofit retroClient) {
        return retroClient.create(FiveDaysForecastService.class);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }
}
