package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.AppPreferencesManager;
import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.CurrentTimeWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;
import com.example.nanorus.materialweather.view.weather.IWeatherActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivityPresenter implements IWeatherActivityPresenter {
    private IWeatherActivity mView;

    private DataManager mDataManager;
    private AppPreferencesManager mAppPreferencesManager;

    // private Observable<ShortDayWeatherPojo> mShortDayWeatherPojoOnlineObservable;
    private Observable<FiveDaysRequestPojo> mRequestPojoObservable;
    private Observable<CurrentTimeWeatherPojo> mCurrentRequestPojoObservable;
    private Observable<ShortDayWeatherPojo> mShortDayWeatherPojoOfflineObservable;
    private Subscription mRequestPojoSubscription;
    private Subscription mCurrentRequestPojoSubscription;
    private Subscription mShortDayWeatherPojoOnlineSubscription;
    private Subscription mShortDayWeatherPojoOfflineSubscription;

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
        mRequestPojoObservable = mDataManager.getFullWeatherOnline(getPlaceFromPref());
        //  mShortDayWeatherPojoOnlineObservable = mDataManager.getDaysWeatherOnline(getPlaceFromPref());
        mCurrentRequestPojoObservable = mDataManager.getCurrentWeather(getPlaceFromPref());

        if (mRequestPojoSubscription != null && !mRequestPojoSubscription.isUnsubscribed())
            mRequestPojoSubscription.unsubscribe();
        if (mShortDayWeatherPojoOnlineSubscription != null && !mShortDayWeatherPojoOnlineSubscription.isUnsubscribed())
            mShortDayWeatherPojoOnlineSubscription.unsubscribe();
        if (mCurrentRequestPojoSubscription != null && !mCurrentRequestPojoSubscription.isUnsubscribed())
            mCurrentRequestPojoSubscription.unsubscribe();

/*
        mShortDayWeatherPojoOnlineSubscription = mShortDayWeatherPojoOnlineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            /*
                            mView.addToWeatherList(shortDayWeatherPojo);
                            mView.updateAdapter();

                        },
                        Throwable::printStackTrace
                );
*/
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
                            /*
                            mView.setWebPlace(requestPojo.getCity().getName() + ", " +
                                    requestPojo.getCity().getCountry());
                            */
                            mDataManager.putFullWeatherData(requestPojo);
                        },
                        Throwable::printStackTrace
                );


    }

    @Override
    public void showData() {
        mShortDayWeatherPojoOfflineObservable = mDataManager.getDaysWeatherOffline();
        mView.createWeatherList();
        mView.setAdapter();
        mShortDayWeatherPojoOfflineSubscription = mShortDayWeatherPojoOfflineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            mView.addToWeatherList(shortDayWeatherPojo);
                            mView.updateAdapter();
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("presenter: SHOW COMPLETED");
                        }
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
        if (mShortDayWeatherPojoOnlineSubscription != null && !mShortDayWeatherPojoOnlineSubscription.isUnsubscribed())
            mShortDayWeatherPojoOnlineSubscription.unsubscribe();
        if (mCurrentRequestPojoSubscription != null && !mCurrentRequestPojoSubscription.isUnsubscribed())
            mCurrentRequestPojoSubscription.unsubscribe();

        mView = null;
        mRequestPojoObservable = null;
    }
}
