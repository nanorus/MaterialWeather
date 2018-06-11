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

    private IWeatherActivity mView;
    private Router mRouter;

    private WeatherForecast mWeatherForecast;
    private Observable<WeatherForecast> mWeatherForecastObservable;
    private Single<WeatherForecast> mWeatherForecastSingle;
    private WeatherInteractor mInteractor;
    private ResourceManager mResourceManager;
    private AppPreferencesManager mAppPreferencesManager;

    @Inject
    public WeatherPresenter(Router router, WeatherInteractor interactor, ResourceManager resourceManager, AppPreferencesManager appPreferencesManager) {
        mRouter = router;
        mInteractor = interactor;
        mResourceManager = resourceManager;
        mAppPreferencesManager = appPreferencesManager;
    }

    @Override
    public void bindView(IWeatherActivity activity) {
        mView = activity;
    }

    @Override
    public void startWork() {
        mView.initForecastList();
        mWeatherForecastObservable = mInteractor.getWeatherForecastUpdates().observeOn(AndroidSchedulers.mainThread());
        mWeatherForecastSingle = mInteractor.getRefreshedWeatherForecast().observeOn(AndroidSchedulers.mainThread());

        mWeatherForecastObservable.subscribe(
                weatherForecast -> {
                    Log.d(TAG, "weatherForecastObservable.onNext");
                    mView.updateWeatherForecast(weatherForecast);
                },
                throwable -> {
                    Log.e(TAG, throwable.toString());
                    Toaster.shortToast(throwable.getMessage());
                }, () -> Log.d(TAG, "weatherForecastObservable.onCompleted"));

    }

    @Override
    public void releasePresenter() {
        mView = null;
    }

    @Override
    public void onSettingsClick() {
        mView.closeDrawer();
        mRouter.startSettingsActivity(mView.getActivity());
    }

    @Override
    public void onResumeView(String showingCity) {
        String savedCity = mAppPreferencesManager.getPlace();
        if (!savedCity.equals(showingCity)) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        mView.showRefresh(true);
        mWeatherForecastSingle = mInteractor.getRefreshedWeatherForecast().observeOn(AndroidSchedulers.mainThread());
        mWeatherForecastSingle.subscribe(
                weatherForecast -> {
                    Log.d(TAG, "weatherForecastSingle.onSuccess");
                    mView.updateWeatherForecast(weatherForecast);
                    mView.setIcon(mResourceManager.getWeatherIcon(weatherForecast.getCurrentWeather().getIcon()));
                    mView.showRefresh(false);
                },
                throwable -> {
                    Log.e(TAG, throwable.toString());
                    Toaster.shortToast(throwable.getMessage());
                    mView.showRefresh(false);
                });
    }
}
