package com.example.nanorus.materialweather.model.data.api.services;

import com.example.nanorus.materialweather.entity.weather.data.ip_location.IPLocation;

import retrofit2.http.GET;
import rx.Single;

public interface IpLocationService {

    @GET("json")
    Single<IPLocation> getIpLocation();
}
