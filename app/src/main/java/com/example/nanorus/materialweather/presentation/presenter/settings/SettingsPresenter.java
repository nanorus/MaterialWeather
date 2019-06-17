package com.example.nanorus.materialweather.presentation.presenter.settings;

import android.util.Log;

import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.model.data.AppPreferencesManager;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.Utils;
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

    ResourceManager resourceManager;
    AppPreferencesManager preferencesManager;
    Router router;
    WeatherRepository weatherRepository;
    SettingsInteractor interactor;

    private StringBuilder enteredCity;
    private StringBuilder previousEnteredCity;

    private ISettingsActivity view;

    @Inject
    public SettingsPresenter(ResourceManager resourceManager, AppPreferencesManager preferencesManager, WeatherRepository weatherRepository, SettingsInteractor interactor, Router router) {
        this.resourceManager = resourceManager;
        this.preferencesManager = preferencesManager;
        this.weatherRepository = weatherRepository;
        this.interactor = interactor;
        this.router = router;
        enteredCity = new StringBuilder();
        previousEnteredCity = new StringBuilder();
    }

    @Override
    public void bindView(ISettingsActivity settingsActivity) {
        view = settingsActivity;
    }

    @Override
    public void startWork() {
        view.setCity(interactor.getCity());
    }

    @Override
    public void onSaveClicked(String locality) {
        if (!locality.isEmpty()) {
            interactor.setCity(locality);
            router.finishActivityWithResult(view.getView(), Router.RESULT_CODE_OK);
        } else {
            Toaster.shortToast(resourceManager.enterLocalityText());
        }
    }

    @Override
    public void onCitiesAutoCompleteItemClicked(String selectedCity) {
        view.showCheckCityProgress(true);
        Single<Boolean> checkСity = interactor.checkCity(selectedCity);
        checkСity.observeOn(AndroidSchedulers.mainThread());
        checkСity.subscribe(new SingleSubscriber<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d(TAG, "check city Success");
                    setCity(selectedCity);
                 //   view.showCheckCityProgress(false);
                    //view.showEnteredCitySuccessNotice(true);
                    view.setCityFoundedTextSuccessful();
                } else {
                    Log.d(TAG, "check city no success");
                    failEnterCity();
                    view.showCheckCityProgress(false);
                    view.setCityFoundedTextUnsuccessful();
                }
            }

            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.toString());
                Toaster.shortToast(error.getMessage());
                if (Utils.check404Error(error)) {
                    failEnterCity();
                } else if (Utils.checkNetWorkError(error)) {
                    Toaster.shortToast(resourceManager.networkError());
                }
                view.showCheckCityProgress(false);
            }
        });

    }

    private void failEnterCity() {
        Toaster.shortToast(resourceManager.cityNotFound());
        view.setEnteredCity(previousEnteredCity.toString());
        view.showEnteredCitySuccessNotice(false);
    }

    @Override
    public void onCitiesAutoCompleteTextChanged(String text) {
        view.showSaveButton(false);
        previousEnteredCity.setLength(0);
        previousEnteredCity.append(enteredCity);
        enteredCity.setLength(0);
        enteredCity.append(text);
        view.hideEnteredCitySuccessNotice();
    }

    @Override
    public void onHomeClicked() {
        router.backPress(view.getView());
    }


    private void setCity(String city) {
        Single<CurrentWeather> nowWeatherOnline = weatherRepository.getRefreshedWeather(city);
        nowWeatherOnline.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<CurrentWeather>() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {
                        Log.d(TAG, "setCity(). get current weather onSuccess");
                        view.setCity(currentWeather.getCity());
                        view.showEnteredCitySuccessNotice(true);
                        view.showSaveButton(true);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "setCity(). get current city Error: " + error.getMessage());
                        view.showSaveButton(false);
                        if (Utils.check404Error(error)) {
                            Toaster.shortToast(resourceManager.cityNotFound());
                            view.setEnteredCity(previousEnteredCity.toString());
                            view.showEnteredCitySuccessNotice(false);
                        } else if (Utils.checkNetWorkError(error)) {
                            Toaster.shortToast(resourceManager.networkError());
                        }
                    }
                });

    }

    @Override
    public void releasePresenter() {
        view = null;
    }
}
