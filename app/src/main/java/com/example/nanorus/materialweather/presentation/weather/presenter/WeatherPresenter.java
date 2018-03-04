package com.example.nanorus.materialweather.presentation.weather.presenter;

import android.util.Log;

import com.example.nanorus.materialweather.data.AppPreferencesManager;
import com.example.nanorus.materialweather.data.ResourceManager;
import com.example.nanorus.materialweather.data.Utils;
import com.example.nanorus.materialweather.data.entity.NowWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.five_days.FiveDaysRequestPojo;
import com.example.nanorus.materialweather.data.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.ui.Toaster;
import com.example.nanorus.materialweather.presentation.weather.view.IWeatherActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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
    private ResourceManager mResourceManager;
    private Router mRouter;

    private List<ShortDayWeatherPojo> mWeekWeatherList;

    private Single<FiveDaysRequestPojo> mOnlineWeekWeatherSingle;
    private Observable<ShortDayWeatherPojo> mOfflineWeekWeatherObservable;
    private Single<NowWeatherPojo> mOnlineNowWeatherSingle;
    private Single<NowWeatherPojo> mOfflineNowWeatherSingle;

    private Subscription mOnlineWeekWeatherSubscription;
    private Subscription mOfflineWeekWeatherSubscription;
    private Subscription mOnlineNowWeatherSubscription;
    private Subscription mOfflineNowWeatherSubscription;

    @Inject
    public WeatherPresenter(WeatherRepository dataManager, AppPreferencesManager appPreferencesManager, Router router, ResourceManager resourceManager) {
        mDataManager = dataManager;
        mAppPreferencesManager = appPreferencesManager;
        mRouter = router;
        mResourceManager = resourceManager;
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
        mView.showRefresh(true);
        mOnlineWeekWeatherSingle = mDataManager.getFiveDaysWeatherOnline(getPlaceFromPref());
        mOnlineNowWeatherSingle = mDataManager.getNowWeatherOnline(getPlaceFromPref());

        if (mOnlineWeekWeatherSubscription != null && !mOnlineWeekWeatherSubscription.isUnsubscribed())
            mOnlineWeekWeatherSubscription.unsubscribe();
        if (mOnlineNowWeatherSubscription != null && !mOnlineNowWeatherSubscription.isUnsubscribed())
            mOnlineNowWeatherSubscription.unsubscribe();


        mOnlineNowWeatherSubscription = mOnlineNowWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nowWeatherPojo -> {
                            mAppPreferencesManager.setNowWeatherData(nowWeatherPojo);
                            if (mOnlineWeekWeatherSubscription.isUnsubscribed()) {
                                updateDataOffline();
                                mView.showRefresh(false);
                            }
                        },
                        throwable -> {
                            if (Utils.check404Error(throwable)) {
                                Toaster.shortToast(mResourceManager.apiPlaceNotFound());
                            }
                        });
        mOnlineWeekWeatherSubscription = mOnlineWeekWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fiveDaysRequestPojo -> {
                            Log.d(TAG, "saving weather started");
                            mDataManager.saveFullWeatherData(fiveDaysRequestPojo);
                            Log.d(TAG, "saving weather completed");
                            if (mOnlineNowWeatherSubscription.isUnsubscribed()) {
                                mAppPreferencesManager.setLastWeatherUpdateTime(new Date());
                                updateDataOffline();
                                mView.showRefresh(false);
                            }
                        },
                        (throwable -> {
                            if (Utils.check404Error(throwable)) {
                                Toaster.shortToast(mResourceManager.apiPlaceNotFound());
                            } else if (Utils.checkNetWorkError(throwable)) {
                                Toaster.shortToast(mResourceManager.networkError());
                            }
                        })
                );
    }

    @Override
    public void updateDataOffline() {
        Log.d(TAG, "updateDataOffline()");
        if (mWeekWeatherList != null)
            mWeekWeatherList.clear();
        else
            mWeekWeatherList = new ArrayList<>();
        mView.createWeatherList(mWeekWeatherList);
        mView.setAdapter();

        mOfflineWeekWeatherObservable = mDataManager.getDaysWeatherOffline();
        mOfflineNowWeatherSingle = mDataManager.loadNowWeatherData();

        if (mOfflineWeekWeatherSubscription != null && !mOfflineWeekWeatherSubscription.isUnsubscribed())
            mOfflineWeekWeatherSubscription.unsubscribe();
        if (mOfflineNowWeatherSubscription != null && !mOfflineNowWeatherSubscription.isUnsubscribed())
            mOfflineNowWeatherSubscription.unsubscribe();

        mView.setLastWeatherUpdateTime(mAppPreferencesManager.getLastWeatherUpdateTime());

        mOfflineNowWeatherSubscription = mOfflineNowWeatherSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nowWeatherPojo -> {
                            Log.d(TAG, "load now offline: place: " + nowWeatherPojo.getPlace());
                            mView.setNowSky(nowWeatherPojo.getDescription());
                            mView.setNowTemperature(String.valueOf(nowWeatherPojo.getTemp()));
                            mView.setWebPlace(nowWeatherPojo.getPlace());
                            setWeatherIcon(nowWeatherPojo.getIcon());
                        }, Throwable::printStackTrace);

        mOfflineWeekWeatherSubscription = mOfflineWeekWeatherObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shortDayWeatherPojo -> {
                            Log.d(TAG, "update list: " + mWeekWeatherList.size());
                            mWeekWeatherList.add(shortDayWeatherPojo);
                            mView.updateAdapter();
                        }, Throwable::printStackTrace,
                        () -> {
                            Log.d(TAG, "list size: " + mWeekWeatherList.size());
                            if (mWeekWeatherList.size() < 4)
                                updateDataOnline();
                        });
    }

    private void setWeatherIcon(String icon) {
        mView.setWeatherIcon(mResourceManager.getWeatherIcon(icon));
    }

    private String getPlaceFromPref() {
        return mAppPreferencesManager.getPlace();
    }

    @Override
    public void releasePresenter() {
        if (mOnlineWeekWeatherSubscription != null && !mOnlineWeekWeatherSubscription.isUnsubscribed())
            mOnlineWeekWeatherSubscription.unsubscribe();
        if (mOnlineNowWeatherSubscription != null && !mOnlineNowWeatherSubscription.isUnsubscribed())
            mOnlineNowWeatherSubscription.unsubscribe();
        if (mOfflineWeekWeatherSubscription != null && !mOfflineWeekWeatherSubscription.isUnsubscribed())
            mOfflineWeekWeatherSubscription.unsubscribe();
        if (mOfflineNowWeatherSubscription != null && !mOfflineNowWeatherSubscription.isUnsubscribed())
            mOfflineNowWeatherSubscription.unsubscribe();


        mView = null;
        mOnlineWeekWeatherSingle = null;
        mOnlineNowWeatherSingle = null;
        mOfflineWeekWeatherObservable = null;
        mOfflineNowWeatherSingle = null;
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

    @Override
    public void onRefresh() {
        updateDataOnline();
    }
}
