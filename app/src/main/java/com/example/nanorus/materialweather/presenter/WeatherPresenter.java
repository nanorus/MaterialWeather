package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.RequestPojo;
import com.example.nanorus.materialweather.utils.AppPreferencesManager;
import com.example.nanorus.materialweather.view.WeatherInterface;

import rx.Observable;
import rx.Subscriber;

public class WeatherPresenter implements WeatherInterface.Action {
    private WeatherInterface.View mView;

    private Observable<RequestPojo> requestPojoObservable;
    private Observable<ListPojo> listPojoObservable;
    private Subscriber<ListPojo> listPojoSubscriber;
    private Subscriber<RequestPojo> requestPojoSubscriber;
    private AppPreferencesManager mAppPreferencesManager;

    public WeatherPresenter(WeatherInterface.View view) {
        mView = view;
        mAppPreferencesManager = new AppPreferencesManager(mView.getView());

        mView.setUserEnteredPlace(getPlaceFromPref());

        loadData();
        showData();
    }

    @Override
    public void loadData() {
        requestPojoObservable = DataManager.getFullForecastFromWeb(getPlaceFromPref());
        listPojoObservable = DataManager.get3hForecastListFromWeb(getPlaceFromPref());
    }

    @Override
    public void showData() {
        mView.createWeatherList();
        mView.setAdapter();
        if (requestPojoSubscriber != null && !requestPojoSubscriber.isUnsubscribed())
            requestPojoSubscriber.unsubscribe();

        if (listPojoSubscriber != null && !listPojoSubscriber.isUnsubscribed())
            listPojoSubscriber.unsubscribe();

        requestPojoSubscriber = new Subscriber<RequestPojo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RequestPojo requestPojo) {
                mView.setWebPlace(requestPojo.getCity().getName() + ", " + requestPojo.getCity().getCountry());
            }
        };

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
                mView.addToWeatherList(forecast3hItem);
                mView.updateAdapter();

            }
        };
        requestPojoObservable.subscribe(requestPojoSubscriber);
        listPojoObservable.subscribe(listPojoSubscriber);

    }

    @Override
    public void onSearchButtonPressed() {
        setPlaceToPref();
        loadData();
        showData();
    }

    @Override
    public void setPlaceToPref() {
        mAppPreferencesManager.setPlace(mView.getUserEnteredPlace());
    }

    @Override
    public String getPlaceFromPref() {
        return mAppPreferencesManager.getPlace();
    }

    @Override
    public void releasePresenter() {
        mView = null;
        if (listPojoSubscriber != null && !listPojoSubscriber.isUnsubscribed())
            listPojoSubscriber.unsubscribe();
        listPojoObservable = null;
        requestPojoObservable = null;
    }
}
