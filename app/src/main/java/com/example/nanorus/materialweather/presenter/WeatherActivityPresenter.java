package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.AppPreferencesManager;
import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.NowWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;
import com.example.nanorus.materialweather.view.weather.IWeatherActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivityPresenter implements IWeatherActivityPresenter {
    private IWeatherActivity mView;

    private DataManager mDataManager;
    private AppPreferencesManager mAppPreferencesManager;

    private List<ShortDayWeatherPojo> mWeatherDaysList;

    private Observable<FiveDaysRequestPojo> mFiveDaysWeatherFullOnlineObservable;
    private Observable<NowWeatherPojo> mNowWeatherOnlineObservable;
    private Observable<ShortDayWeatherPojo> mFiveDaysWeatherShortOfflineObservable;
    private Single<NowWeatherPojo> mNowWeatherOfflineSingle;
    private Subscription mFiveDaysWeatherFullOnlineSubscription;
    private Subscription mNowWeatherOnlineSubscription;
    private Subscription mFiveDaysWeatherShortOfflineSubscription;
    private Subscription mNowWeatherOfflineSubscription;
    private Subscription mSaveDataSubscription;

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
        updateDataOnline();
    }

    @Override
    public void updateDataOnline() {
        System.out.println("\n\n= PRESENTER: ONLINE LOADING =");
        mFiveDaysWeatherFullOnlineObservable = mDataManager.getFiveDaysWeatherOnline(getPlaceFromPref());
        mNowWeatherOnlineObservable = mDataManager.getNowWeatherOnline(getPlaceFromPref());

        if (mFiveDaysWeatherFullOnlineSubscription != null && !mFiveDaysWeatherFullOnlineSubscription.isUnsubscribed())
            mFiveDaysWeatherFullOnlineSubscription.unsubscribe();
        if (mNowWeatherOnlineSubscription != null && !mNowWeatherOnlineSubscription.isUnsubscribed())
            mNowWeatherOnlineSubscription.unsubscribe();

        mNowWeatherOnlineSubscription = mNowWeatherOnlineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nowWeatherPojo -> mAppPreferencesManager.saveNowWeatherData(nowWeatherPojo),
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("presenter: updateDataOnline(): Now Online loaded;");
                            if (mFiveDaysWeatherFullOnlineSubscription.isUnsubscribed())
                                updateDataOffline();
                        }
                );
        mFiveDaysWeatherFullOnlineSubscription = mFiveDaysWeatherFullOnlineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        requestPojo -> {
                            System.out.println("presenter: updateDataOnline(): Full Online loaded;");
                            if (mSaveDataSubscription != null && !mSaveDataSubscription.isUnsubscribed()) {
                                mSaveDataSubscription.unsubscribe();
                            }
                            mSaveDataSubscription =
                                    Completable.create(completableSubscriber -> {
                                        System.out.println("= PRESENTER: SAVING =");
                                        mDataManager.saveFullWeatherData(requestPojo);
                                        completableSubscriber.onCompleted();
                                    })
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> {
                                                        System.out.println("presenter: updateDataOnline(): saving completed;");
                                                        if (mNowWeatherOnlineSubscription.isUnsubscribed())
                                                            updateDataOffline();
                                                    }, Throwable::printStackTrace
                                            );

                        },
                        Throwable::printStackTrace,
                        () -> {
                        });
    }

    @Override
    public void updateDataOffline() {
        System.out.println("= PRESENTER: OFFLINE LOADING =");
        if (mWeatherDaysList != null)
            mWeatherDaysList.clear();
        else
            mWeatherDaysList = new ArrayList<>();
        mView.createWeatherList(mWeatherDaysList);
        mView.setAdapter();

        mFiveDaysWeatherShortOfflineObservable = mDataManager.getDaysWeatherOffline();
        mNowWeatherOfflineSingle = mDataManager.loadNowWeatherData();

        if (mFiveDaysWeatherShortOfflineSubscription != null && !mFiveDaysWeatherShortOfflineSubscription.isUnsubscribed())
            mFiveDaysWeatherShortOfflineSubscription.unsubscribe();
        if (mNowWeatherOfflineSubscription != null && !mNowWeatherOfflineSubscription.isUnsubscribed())
            mNowWeatherOfflineSubscription.unsubscribe();

        mNowWeatherOfflineSubscription = mNowWeatherOfflineSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nowWeatherPojo -> {
                            System.out.println("presenter: load now offline: place: " + nowWeatherPojo.getPlace());
                            mView.setNowSky(nowWeatherPojo.getDescription());
                            mView.setNowTemperature(String.valueOf(nowWeatherPojo.getTemp()));
                            mView.setWebPlace(nowWeatherPojo.getPlace());
                        }, Throwable::printStackTrace);

        mFiveDaysWeatherShortOfflineSubscription = mFiveDaysWeatherShortOfflineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            System.out.println("presenter:update list: " + mWeatherDaysList.size());
                            mWeatherDaysList.add(shortDayWeatherPojo);
                            mView.updateAdapter();
                        }, Throwable::printStackTrace,
                        () -> {
                            System.out.println("presenter:list size: " + mWeatherDaysList.size());
                            if (mWeatherDaysList.size() < 4)
                                updateDataOnline();
                        });
    }


    @Override
    public void onSearchButtonPressed() {
        setPlaceToPref();
        updateDataOnline();
    }

    @Override
    public void setPlaceToPref() {
        mAppPreferencesManager.savePlace(mView.getUserEnteredPlace());
    }

    @Override
    public String getPlaceFromPref() {
        return mAppPreferencesManager.loadPlace();
    }

    @Override
    public void releasePresenter() {
        if (mFiveDaysWeatherFullOnlineSubscription != null && !mFiveDaysWeatherFullOnlineSubscription.isUnsubscribed())
            mFiveDaysWeatherFullOnlineSubscription.unsubscribe();
        if (mNowWeatherOnlineSubscription != null && !mNowWeatherOnlineSubscription.isUnsubscribed())
            mNowWeatherOnlineSubscription.unsubscribe();
        if (mFiveDaysWeatherShortOfflineSubscription != null && !mFiveDaysWeatherShortOfflineSubscription.isUnsubscribed())
            mFiveDaysWeatherShortOfflineSubscription.unsubscribe();
        if (mNowWeatherOfflineSubscription != null && !mNowWeatherOfflineSubscription.isUnsubscribed())
            mNowWeatherOfflineSubscription.unsubscribe();


        mView = null;
        mFiveDaysWeatherFullOnlineObservable = null;
        mNowWeatherOnlineObservable = null;
        mFiveDaysWeatherShortOfflineObservable = null;
        mNowWeatherOfflineSingle = null;
    }
}
