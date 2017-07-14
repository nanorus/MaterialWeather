package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.RequestPojo;
import com.example.nanorus.materialweather.utils.AppPreferencesManager;
import com.example.nanorus.materialweather.view.IWeatherActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class WeatherPresenter implements IWeatherPresenter {
    private IWeatherActivity mView;

    private Observable<RequestPojo> requestPojoObservable;
    private Observable<ListPojo> listPojoObservable;
    private Subscriber<ListPojo> listPojoSubscriber;
    private Subscriber<RequestPojo> requestPojoSubscriber;
    private AppPreferencesManager mAppPreferencesManager;

    public WeatherPresenter(IWeatherActivity view) {
        mView = view;
        mAppPreferencesManager = new AppPreferencesManager(mView.getActivity());

        mView.setUserEnteredPlace(getPlaceFromPref());

        loadData();
        showData();
    }

    @Override
    public void loadData() {
        requestPojoObservable = DataManager.getFullForecastData(getPlaceFromPref());
        listPojoObservable = DataManager.get3hForecastData(getPlaceFromPref());
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
                requestPojoSubscriber.unsubscribe();
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
                System.out.println("list onNext: " + forecast3hItem.getDtTxt() + ": " + forecast3hItem.getMain().getTemp());
            }
        };
        requestPojoObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(requestPojoSubscriber);
        listPojoObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listPojoSubscriber);

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
