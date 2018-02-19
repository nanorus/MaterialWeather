package com.example.nanorus.materialweather.presentation.weather.presenter;

import android.util.Log;

import com.example.nanorus.materialweather.data.AppPreferencesManager;
import com.example.nanorus.materialweather.data.Utils;
import com.example.nanorus.materialweather.data.entity.NowWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.five_days.FiveDaysRequestPojo;
import com.example.nanorus.materialweather.data.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.weather.view.IWeatherActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherPresenter implements IWeatherPresenter {
    private final String TAG = this.getClass().getSimpleName();

    private IWeatherActivity mView;
    private WeatherRepository mDataManager;
    private AppPreferencesManager mAppPreferencesManager;
    private Router mRouter;

    private List<ShortDayWeatherPojo> mWeatherDaysList;

    private Observable<FiveDaysRequestPojo> mFiveDaysWeatherFullOnlineObservable;
    private Single<NowWeatherPojo> mNowWeatherOnlineSingle;
    private Observable<ShortDayWeatherPojo> mFiveDaysWeatherShortOfflineObservable;
    private Single<NowWeatherPojo> mNowWeatherOfflineSingle;
    private Subscription mFiveDaysWeatherFullOnlineSubscription;
    private Subscription mNowWeatherOnlineSubscription;
    private Subscription mFiveDaysWeatherShortOfflineSubscription;
    private Subscription mNowWeatherOfflineSubscription;
    private Subscription mSaveDataSubscription;

    @Inject
    public WeatherPresenter(WeatherRepository dataManager, AppPreferencesManager appPreferencesManager, Router router) {
        mDataManager = dataManager;
        mAppPreferencesManager = appPreferencesManager;
        mRouter = router;
    }

    @Override
    public void bindView(IWeatherActivity activity) {
        mView = activity;
    }

    @Override
    public void startWork() {
        updateDataOnline();
    }

    @Override
    public void updateDataOnline() {
        Log.d(TAG, "updateDataOnline()");
        mFiveDaysWeatherFullOnlineObservable = mDataManager.getFiveDaysWeatherOnline(getPlaceFromPref());
        mNowWeatherOnlineSingle = mDataManager.getNowWeatherOnline(getPlaceFromPref());

        if (mFiveDaysWeatherFullOnlineSubscription != null && !mFiveDaysWeatherFullOnlineSubscription.isUnsubscribed())
            mFiveDaysWeatherFullOnlineSubscription.unsubscribe();
        if (mNowWeatherOnlineSubscription != null && !mNowWeatherOnlineSubscription.isUnsubscribed())
            mNowWeatherOnlineSubscription.unsubscribe();

        mNowWeatherOnlineSubscription = mNowWeatherOnlineSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nowWeatherPojo -> mAppPreferencesManager.setNowWeatherData(nowWeatherPojo),
                        throwable -> {
                            if (Utils.check404Error(throwable)) {
                                // TODO: online loading ip city
                                mAppPreferencesManager.setPlace("Moscow");
                                updateDataOnline();
                            }
                        });
        mFiveDaysWeatherFullOnlineSubscription = mFiveDaysWeatherFullOnlineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        requestPojo -> {
                            Log.d(TAG, "Full Online loaded");
                            if (mSaveDataSubscription != null && !mSaveDataSubscription.isUnsubscribed()) {
                                mSaveDataSubscription.unsubscribe();
                            }
                            mSaveDataSubscription =
                                    Completable.create(completableSubscriber -> {
                                        Log.d(TAG, "Saving");
                                        mDataManager.saveFullWeatherData(requestPojo);
                                        completableSubscriber.onCompleted();
                                    })
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> {
                                                        Log.d(TAG, "saving completed");
                                                        if (mNowWeatherOnlineSubscription.isUnsubscribed())
                                                            updateDataOffline();
                                                    }, Throwable::printStackTrace
                                            );

                        },
                        (throwable -> {
                            // TODO: online loading ip city
                        }),
                        () -> {
                        });
    }

    @Override
    public void updateDataOffline() {
        Log.d(TAG, "updateDataOffline()");
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
                            Log.d(TAG, "load now offline: place: " + nowWeatherPojo.getPlace());
                            mView.setNowSky(nowWeatherPojo.getDescription());
                            mView.setNowTemperature(String.valueOf(nowWeatherPojo.getTemp()));
                            mView.setWebPlace(nowWeatherPojo.getPlace());
                        }, Throwable::printStackTrace);

        mFiveDaysWeatherShortOfflineSubscription = mFiveDaysWeatherShortOfflineObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            Log.d(TAG, "update list: " + mWeatherDaysList.size());
                            mWeatherDaysList.add(shortDayWeatherPojo);
                            mView.updateAdapter();
                        }, Throwable::printStackTrace,
                        () -> {
                            Log.d(TAG, "list size: " + mWeatherDaysList.size());
                            if (mWeatherDaysList.size() < 4)
                                updateDataOnline();
                        });
    }

    private String getPlaceFromPref() {
        return mAppPreferencesManager.getPlace();
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
        mNowWeatherOnlineSingle = null;
        mFiveDaysWeatherShortOfflineObservable = null;
        mNowWeatherOfflineSingle = null;
    }

    @Override
    public void onSettingsClick() {
        mView.closeDrawer();
        mRouter.startSettingsActivity(mView.getActivity());
    }

    @Override
    public void onResumeView(String showingCity) {
        String savedCity = mAppPreferencesManager.getPlace();
        if (!savedCity.equals(showingCity))
            updateDataOnline();
    }
}
