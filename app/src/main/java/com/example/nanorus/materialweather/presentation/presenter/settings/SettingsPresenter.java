package com.example.nanorus.materialweather.presentation.presenter.settings;

import android.util.Log;

import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.Utils;
import com.example.nanorus.materialweather.model.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.model.domain.settings.SettingsInteractor;
import com.example.nanorus.materialweather.model.repository.weather.WeatherRepository;
import com.example.nanorus.materialweather.navigation.Router;
import com.example.nanorus.materialweather.presentation.ui.Toaster;
import com.example.nanorus.materialweather.presentation.view.settings.ISettingsActivity;

import javax.inject.Inject;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsPresenter implements ISettingsPresenter {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    ResourceManager mResourceManager;
    @Inject
    AppPreferencesManager mPreferencesManager;
    @Inject
    Router mRouter;
    @Inject
    WeatherRepository mWeatherRepository;
    @Inject
    SettingsInteractor interactor;

    private StringBuilder mEnteredCity;
    private StringBuilder mPreviousEnteredCity;

    private ISettingsActivity mView;

    public SettingsPresenter() {
        App.getApp().getAppComponent().inject(this);
        mEnteredCity = new StringBuilder();
        mPreviousEnteredCity = new StringBuilder();
    }

    @Override
    public void bindView(ISettingsActivity settingsActivity) {
        mView = settingsActivity;
    }

    @Override
    public void startWork() {
        mView.setCity(interactor.getCity());
    }

    @Override
    public void onSaveClicked(String locality) {
        if (!locality.isEmpty()) {
            interactor.setCity(locality);
            mRouter.finishActivity(mView.getView());
        } else {
            Toaster.shortToast(mResourceManager.enterLocalityText());
        }
    }

    @Override
    public void onCitiesAutoCompleteItemClicked(String selectedCity) {
        Single<Boolean> checkSity = interactor.checkCity(selectedCity);
        checkSity.observeOn(AndroidSchedulers.mainThread());
        checkSity.subscribe(new SingleSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d(TAG, "check city Success");
                    setCity(selectedCity);
                    mView.showEnteredCitySuccessNotice(true);
                } else {
                    Log.d(TAG, "check city no success");
                    failEnterCity();
                }
            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.toString());
                Toaster.shortToast(error.getMessage());
                if (Utils.check404Error(error)) {
                    failEnterCity();
                } else if (Utils.checkNetWorkError(error)) {
                    Toaster.shortToast(mResourceManager.networkError());
                }
            }
        });

    }

    private void failEnterCity() {
        Toaster.shortToast(mResourceManager.cityNotFound());
        mView.setEnteredCity(mPreviousEnteredCity.toString());
        mView.showEnteredCitySuccessNotice(false);
    }

    @Override
    public void onCitiesAutoCompleteTextChanged(String text) {
        mView.showSaveButton(false);
        mPreviousEnteredCity.setLength(0);
        mPreviousEnteredCity.append(mEnteredCity);
        mEnteredCity.setLength(0);
        mEnteredCity.append(text);
        mView.hideEnteredCitySuccessHotice();
    }

    @Override
    public void onHomeClicked() {
        mRouter.backPress(mView.getView());
    }


    private void setCity(String city) {
        Single<CurrentWeather> nowWeatherOnline = mWeatherRepository.getRefreshedWeather(city);
        nowWeatherOnline.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<CurrentWeather>() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        Log.d(TAG, "setCity(). get current weather onSuccess");
                        mView.setCity(currentWeather.getCity());
                        mView.showEnteredCitySuccessNotice(true);
                        mView.showSaveButton(true);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "setCity(). get current city Error: " + error.getMessage());
                        mView.showSaveButton(false);
                        if (Utils.check404Error(error)) {
                            Toaster.shortToast(mResourceManager.cityNotFound());
                            mView.setEnteredCity(mPreviousEnteredCity.toString());
                            mView.showEnteredCitySuccessNotice(false);
                        } else if (Utils.checkNetWorkError(error)) {
                            Toaster.shortToast(mResourceManager.networkError());
                        }
                    }
                });

    }

    @Override
    public void releasePresenter() {
        mView = null;
    }
}
