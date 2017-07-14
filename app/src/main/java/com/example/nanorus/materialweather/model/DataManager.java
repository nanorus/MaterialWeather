package com.example.nanorus.materialweather.model;

import com.example.nanorus.materialweather.model.api.WeatherRetroClient;
import com.example.nanorus.materialweather.model.api.services.ForecastService;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.RequestPojo;

import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DataManager {

    public static Observable<RequestPojo> getFullForecastData(String place) {
        Retrofit retrofit = WeatherRetroClient.getInstance();
        ForecastService service = retrofit.create(ForecastService.class);

        // all data (RequestPojo)
        Observable<RequestPojo> requestPojoObservable = service.getRequestPojoObservable(place)
                .subscribeOn(Schedulers.io());
        return requestPojoObservable;
    }

    public static Observable<ListPojo> get3hForecastData(String place) {
        // list for 3 hours each (ListPojo)
        Observable<ListPojo> listPojoObservable = getFullForecastData(place)
                .map(requestPojo -> (requestPojo.getList()))
                .flatMap(listPojos -> Observable.from(listPojos));
        return listPojoObservable;
    }

}
