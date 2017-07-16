package com.example.nanorus.materialweather.model.api.services;

import com.example.nanorus.materialweather.model.pojo.forecast.api.RequestPojo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ForecastService {


    @GET("forecast?mode=json&appid=1c5db98abd4a84a894d55cda56a02f1c")
    Observable<RequestPojo> getRequestPojoObservable (@Query("q") String place);
  //  Call<RequestPojo> getJsonPojo(@Query("q") String place);

    //Call<String> getJsonPojo();

   // Call<String> getJsonPojo(@Query("q") String place);

}
