package com.example.nanorus.materialweather.model.api.services;

import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FiveDaysForecastService {

    @GET("forecast?mode=json&appid=1c5db98abd4a84a894d55cda56a02f1c")
    Observable<FiveDaysRequestPojo> getRequestPojoObservable (@Query("q") String place);

}
