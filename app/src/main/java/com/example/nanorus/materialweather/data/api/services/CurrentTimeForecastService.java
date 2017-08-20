package com.example.nanorus.materialweather.data.api.services;

import com.example.nanorus.materialweather.data.entity.forecast.api.current_time.CurrentRequestPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CurrentTimeForecastService {

    @GET("weather?mode=json&appid=1c5db98abd4a84a894d55cda56a02f1c")
    Observable<CurrentRequestPojo> getRequestPojoObservable (@Query("q") String place);
}
