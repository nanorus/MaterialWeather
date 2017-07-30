package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.RequestPojo;
import com.example.nanorus.materialweather.utils.AppPreferencesManager;
import com.example.nanorus.materialweather.view.IWeatherActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivityPresenter implements IWeatherActivityPresenter {
    private IWeatherActivity mView;

    Observable<ShortDayWeatherPojo> mShortDayWeatherPojoObservable;
    private Observable<RequestPojo> mRequestPojoObservable;
    private Subscriber<RequestPojo> mRequestPojoSubscriber;
    private Subscriber<ShortDayWeatherPojo> mShortDayWeatherPojoSubscriber;
    private AppPreferencesManager mAppPreferencesManager;

    public WeatherActivityPresenter(IWeatherActivity view) {
        mView = view;
        mAppPreferencesManager = new AppPreferencesManager(mView.getActivity());

        mView.setUserEnteredPlace(getPlaceFromPref());

        loadData();
        showData();
    }

    @Override
    public void loadData() {
        mRequestPojoObservable = DataManager.getFullForecastData(getPlaceFromPref());
        mShortDayWeatherPojoObservable = DataManager.getShortDayWeatherPojos(getPlaceFromPref());
    }

    @Override
    public void showData() {
        mView.createWeatherList();
        mView.setAdapter();
        if (mRequestPojoSubscriber != null && !mRequestPojoSubscriber.isUnsubscribed())
            mRequestPojoSubscriber.unsubscribe();
        if (mShortDayWeatherPojoSubscriber != null && !mShortDayWeatherPojoSubscriber.isUnsubscribed())
            mShortDayWeatherPojoSubscriber.unsubscribe();

        mRequestPojoSubscriber = new Subscriber<RequestPojo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RequestPojo requestPojo) {
                mView.setWebPlace(requestPojo.getCity().getName() + ", " + requestPojo.getCity().getCountry());
            }
        };

        mShortDayWeatherPojoSubscriber = (new Subscriber<ShortDayWeatherPojo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ShortDayWeatherPojo shortDayWeatherPojo) {
                // addToListOfAdapter
                mView.addToWeatherList(shortDayWeatherPojo);
                mView.updateAdapter();
            }
        });

        mRequestPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRequestPojoSubscriber);

        mShortDayWeatherPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mShortDayWeatherPojoSubscriber);

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
        if (mRequestPojoSubscriber != null && !mRequestPojoSubscriber.isUnsubscribed())
            mRequestPojoSubscriber.unsubscribe();
        if (mShortDayWeatherPojoSubscriber != null && !mShortDayWeatherPojoSubscriber.isUnsubscribed())
            mShortDayWeatherPojoSubscriber.unsubscribe();
        mView = null;
        mRequestPojoObservable = null;
    }
}
