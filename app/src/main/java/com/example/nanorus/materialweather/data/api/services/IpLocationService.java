package com.example.nanorus.materialweather.data.api.services;

import com.example.nanorus.materialweather.data.entity.ip_location.IPLocation;

import retrofit2.http.GET;
import rx.Single;

public interface IpLocationService {

    @GET("json")
    Single<IPLocation> getIpLocation();
}
