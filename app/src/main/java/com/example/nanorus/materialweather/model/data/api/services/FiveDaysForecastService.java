package com.example.nanorus.materialweather.model.data.api.services;

import com.example.nanorus.materialweather.entity.data.five_days.FiveDaysRequestPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

public interface FiveDaysForecastService {

    @GET("forecast?mode=json&appid=1c5db98abd4a84a894d55cda56a02f1c")
    Single<FiveDaysRequestPojo> getRequestPojoSingle (@Query("q") String place);

}
