package com.example.nanorus.materialweather.model.data.api.services;

import com.example.nanorus.materialweather.entity.weather.data.search_possible_cities.PredictionList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchPossibleCitiesService {

    @GET("json?key=AIzaSyBYFEDpnNI1nP3qGtJcdcsX__rnPoR-IYQ&types=(cities)&radius=50000")
    Observable<PredictionList> getPossibleCities(@Query("input") String input, @Query("location") String location);

}
