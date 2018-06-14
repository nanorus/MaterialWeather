package com.example.nanorus.materialweather.presentation.presenter.weather;

import android.util.Log;

import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;
import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.domain.weather.WeatherInteractor;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.ui.Toaster;
import com.example.nanorus.materialweather.presentation.view.weather.IWeatherActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;

public class WeatherPresenter implements IWeatherPresenter {
    private final String TAG = this.getClass().getSimpleName();

    private IWeatherActivity view;
    private Router router;

    private Observable<WeatherForecast> weatherForecastObservable;
    private Single<WeatherForecast> refreshedWeatherForecastSingle;
    private Single<WeatherForecast> cachedWeatherForecastSingle;
    private WeatherInteractor interactor;
    private ResourceManager resourceManager;
    private AppPreferencesManager appPreferencesManager;

    @Inject
    public WeatherPresenter(Router router, WeatherInteractor interactor, ResourceManager resourceManager, AppPreferencesManager appPreferencesManager) {
        this.router = router;
        this.interactor = interactor;
        this.resourceManager = resourceManager;
        this.appPreferencesManager = appPreferencesManager;
    }

    @Override
    public void bindView(IWeatherActivity activity) {
        view = activity;
    }

    @Override
    public void startWork() {
        view.initForecastList();
        weatherForecastObservable = interactor.getWeatherForecastUpdates().observeOn(AndroidSchedulers.mainThread());
        refreshedWeatherForecastSingle = interactor.getRefreshedWeatherForecast().observeOn(AndroidSchedulers.mainThread());
        cachedWeatherForecastSingle = interactor.loadWeatherForecast().observeOn(AndroidSchedulers.mainThread());

        cachedWeatherForecastSingle.subscribe(
                weatherForecast -> {
                    Log.d(TAG, "cachedWeatherForecastSingle.onSuccess");
                    view.updateWeatherForecast(weatherForecast);
                    view.setIcon(resourceManager.getWeatherIcon(weatherForecast.getCurrentWeather().getIcon()));
                    view.showRefresh(false);
                    view.scrollToTop();
                },
                throwable -> {
                    Log.e(TAG, throwable.toString());
                    Toaster.shortToast(throwable.getMessage());
                    view.showRefresh(false);
                    view.scrollToTop();
                }
        );

        weatherForecastObservable.subscribe(
                weatherForecast -> {
                    Log.d(TAG, "weatherForecastObservable.onNext");
                    view.updateWeatherForecast(weatherForecast);
                },
                throwable -> {
                    Log.e(TAG, throwable.toString());
                    Toaster.shortToast(throwable.getMessage());
                }, () -> Log.d(TAG, "weatherForecastObservable.onCompleted"));
    }

    @Override
    public void releasePresenter() {
        view = null;
    }

    @Override
    public void onSettingsClick() {
        view.closeDrawer();
        router.startSettingsActivity(view.getActivity());
    }

    @Override
    public void onRefresh() {
        view.showRefresh(true);
        refreshedWeatherForecastSingle = interactor.getRefreshedWeatherForecast().observeOn(AndroidSchedulers.mainThread());
        refreshedWeatherForecastSingle.subscribe(
                weatherForecast -> {
                    Log.d(TAG, "refreshedWeatherForecastSingle.onSuccess");
                    view.updateWeatherForecast(weatherForecast);
                    view.setIcon(resourceManager.getWeatherIcon(weatherForecast.getCurrentWeather().getIcon()));
                    view.showRefresh(false);
                    view.scrollToTop();
                },
                throwable -> {
                    Log.e(TAG, throwable.toString());
                    Toaster.shortToast(throwable.getMessage());
                    view.showRefresh(false);
                    view.scrollToTop();
                });
    }

    @Override
    public void onActivityResult(boolean isResultOk, String showingCity) {
        if (isResultOk){
            String savedCity = appPreferencesManager.getPlace();
            if (!savedCity.equals(showingCity)) {
                onRefresh();
            }
        }
    }
}
