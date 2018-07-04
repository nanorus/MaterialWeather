package com.example.nanorus.materialweather.presentation.presenter.dayforecast;

import android.util.Log;

import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.domain.dayforecast.DayForecastInteractor;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.view.dayforecast.DayForecastActivity;

import java.util.Date;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

public class DayForecastPresenter {
    private String TAG = this.getClass().getName();
    private Single<DayForecast> dataSingle;
    private DayForecastInteractor interactor;
    private ResourceManager resourceManager;
    private DayForecastActivity view;
    private Router router;
    private Date date;

    @Inject
    public DayForecastPresenter(DayForecastInteractor interactor, ResourceManager resourceManager, Router router) {
        this.interactor = interactor;
        this.resourceManager = resourceManager;
        this.router = router;
    }

    public void startPresenter(Date date) {
        view.showRefreshing(true);
        this.date = date;
        view.initForecast();
        dataSingle = interactor.getDayForecast(this.date).observeOn(AndroidSchedulers.mainThread());
        dataSingle.subscribe(new SingleSubscriber<DayForecast>() {
            @Override
            public void onSuccess(DayForecast dayForecast) {
                Log.d(TAG, "getDayForecast successful");
                onForecastLoaded(dayForecast);
                view.showRefreshing(false);
            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.toString());
                view.showRefreshing(false);
            }
        });
    }

    public void bindView(DayForecastActivity activity) {
        view = activity;
    }

    public void onBackPressed(){
        router.backPress(view.getView());
    }


    public void onSwipeRefresh() {
        dataSingle = interactor.getRefreshedDayForecast(this.date).observeOn(AndroidSchedulers.mainThread());
        dataSingle.subscribe(new SingleSubscriber<DayForecast>() {
            @Override
            public void onSuccess(DayForecast dayForecast) {
                Log.d(TAG, "onSwipe getDayForecast successful");
                onForecastLoaded(dayForecast);
                view.showRefreshing(false);
            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.toString());
                view.showRefreshing(false);
            }
        });
    }

    private void onForecastLoaded(DayForecast dayForecast){
        dayForecast.setPlace(interactor.getPlace());
        view.setLastUpdate(interactor.getLastUpdate());
        view.setInfo(dayForecast);
        view.updateForecast(dayForecast.getHourForecastList());
        view.setToolbarTitle(DateUtils.getDayString(dayForecast.getDate()));
    }
}
