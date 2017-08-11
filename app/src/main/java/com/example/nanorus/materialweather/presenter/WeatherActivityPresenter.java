package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.AppPreferencesManager;
import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.CurrentTimeWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;
import com.example.nanorus.materialweather.view.IWeatherActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivityPresenter implements IWeatherActivityPresenter {
    private IWeatherActivity mView;

    private DataManager mDataManager;
    private AppPreferencesManager mAppPreferencesManager;

    private Observable<ShortDayWeatherPojo> mShortDayWeatherPojoObservable;
    private Observable<FiveDaysRequestPojo> mRequestPojoObservable;
    private Observable<CurrentTimeWeatherPojo> mCurrentRequestPojoObservable;
    private Subscription mRequestPojoSubscription;
    private Subscription mCurrentRequestPojoSubscription;
    private Subscription mShortDayWeatherPojoSubscription;

    @Inject
    public WeatherActivityPresenter(DataManager dataManager, AppPreferencesManager appPreferencesManager) {
        mDataManager = dataManager;
        mAppPreferencesManager = appPreferencesManager;

    }

    @Override
    public void bindView(IWeatherActivity activity) {
        mView = activity;
    }

    @Override
    public void startWork() {
        mView.setUserEnteredPlace(getPlaceFromPref());
        loadData();
        showData();
    }

    @Override
    public void loadData() {
        mRequestPojoObservable = mDataManager.getFullForecastData(getPlaceFromPref());
        mShortDayWeatherPojoObservable = mDataManager.getShortDayWeatherPojos(getPlaceFromPref());
        mCurrentRequestPojoObservable = mDataManager.getCurrentWeather(getPlaceFromPref());
    }

    @Override
    public void showData() {
        mView.createWeatherList();
        mView.setAdapter();

        if (mRequestPojoSubscription != null && !mRequestPojoSubscription.isUnsubscribed())
            mRequestPojoSubscription.unsubscribe();
        if (mShortDayWeatherPojoSubscription != null && !mShortDayWeatherPojoSubscription.isUnsubscribed())
            mShortDayWeatherPojoSubscription.unsubscribe();
        if (mCurrentRequestPojoSubscription != null && !mCurrentRequestPojoSubscription.isUnsubscribed())
            mCurrentRequestPojoSubscription.unsubscribe();

        mShortDayWeatherPojoSubscription = mShortDayWeatherPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            mView.addToWeatherList(shortDayWeatherPojo);
                            mView.updateAdapter();
                        },
                        Throwable::printStackTrace
                );

        mCurrentRequestPojoSubscription = mCurrentRequestPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        currentTimeWeatherPojo -> {
                            mView.setNowSky(currentTimeWeatherPojo.getDescription());
                            mView.setNowTemperature(String.valueOf(currentTimeWeatherPojo.getTemp()));
                            mView.setWebPlace(currentTimeWeatherPojo.getPlace());
                        },
                        Throwable::printStackTrace
                );

        mRequestPojoSubscription = mRequestPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        requestPojo -> {
                            mView.setWebPlace(requestPojo.getCity().getName() + ", " +
                                    requestPojo.getCity().getCountry());
                        },
                        Throwable::printStackTrace
                );

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
        if (mRequestPojoSubscription != null && !mRequestPojoSubscription.isUnsubscribed())
            mRequestPojoSubscription.unsubscribe();
        if (mShortDayWeatherPojoSubscription != null && !mShortDayWeatherPojoSubscription.isUnsubscribed())
            mShortDayWeatherPojoSubscription.unsubscribe();
        if (mCurrentRequestPojoSubscription != null && !mCurrentRequestPojoSubscription.isUnsubscribed())
            mCurrentRequestPojoSubscription.unsubscribe();

        mView = null;
        mRequestPojoObservable = null;
    }
}
