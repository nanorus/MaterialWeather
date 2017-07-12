package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;
import com.example.nanorus.materialweather.view.WeatherInterface;

import rx.Observable;
import rx.Subscriber;

public class WeatherPresenter implements WeatherInterface.Action {
    WeatherInterface.View mView;
    Subscriber<ListPojo> listPojoSubscriber;

    public WeatherPresenter(WeatherInterface.View view) {
        mView = view;

        loadData();
        showData();
    }

    @Override
    public void setPlaceToPref(String place) {

    }

    @Override
    public String getPlaceFromPref() {
        return "Moscow";
    }

    @Override
    public void loadData() {
        Observable<ListPojo> listPojoObservable = DataManager.get3hForecastListFromWeb(getPlaceFromPref());

        if (listPojoSubscriber != null && !listPojoSubscriber.isUnsubscribed())
            listPojoSubscriber.unsubscribe();

        listPojoSubscriber = new Subscriber<ListPojo>() {
            @Override
            public void onCompleted() {
                listPojoSubscriber.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListPojo forecast3hItem) {
                // add to adapter
            }
        };
        listPojoObservable.subscribe(listPojoSubscriber);


    }

    @Override
    public void showData() {

    }

    @Override
    public void releasePresenter() {
        mView = null;
        if (listPojoSubscriber != null && !listPojoSubscriber.isUnsubscribed())
            listPojoSubscriber.unsubscribe();
    }
}
