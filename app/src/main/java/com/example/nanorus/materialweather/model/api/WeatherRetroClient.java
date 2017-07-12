package com.example.nanorus.materialweather.model.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetroClient {

   private static Retrofit sInstance = null;

    public static Retrofit getInstance() {
        if (sInstance == null) {
            sInstance = new Retrofit.Builder()
                   .baseUrl("http://api.openweathermap.org/data/2.5/")
                   .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   // .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return sInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        sInstance = null;
        super.finalize();
    }
}
