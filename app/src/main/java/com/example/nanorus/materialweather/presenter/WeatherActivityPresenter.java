package com.example.nanorus.materialweather.presenter;

import com.example.nanorus.materialweather.model.AppPreferencesManager;
import com.example.nanorus.materialweather.model.DataConverter;
import com.example.nanorus.materialweather.model.DataManager;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.RequestPojo;
import com.example.nanorus.materialweather.view.IWeatherActivity;

import java.util.Date;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherActivityPresenter implements IWeatherActivityPresenter {
    private IWeatherActivity mView;

    Observable<ShortDayWeatherPojo> mShortDayWeatherPojoObservable;
    private Observable<RequestPojo> mRequestPojoObservable;
    private Subscription mRequestPojoSubscription;
    private Subscription mShortDayWeatherPojoSubscription;

    public WeatherActivityPresenter(IWeatherActivity view) {
        mView = view;

        mView.setUserEnteredPlace(getPlaceFromPref());

        loadData();
        showData();
    }

    @Override
    public void loadData() {
        mRequestPojoObservable = DataManager.getFullForecastData(getPlaceFromPref());
        mShortDayWeatherPojoObservable = DataManager.getShortDayWeatherPojos(getPlaceFromPref());
    }

    @Override
    public void showData() {
        mView.createWeatherList();
        mView.setAdapter();

        if (mRequestPojoSubscription != null && !mRequestPojoSubscription.isUnsubscribed())
            mRequestPojoSubscription.unsubscribe();
        if (mShortDayWeatherPojoSubscription != null && !mShortDayWeatherPojoSubscription.isUnsubscribed())
            mShortDayWeatherPojoSubscription.unsubscribe();

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

        mRequestPojoSubscription = mRequestPojoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        requestPojo -> {
                            mView.setWebPlace(requestPojo.getCity().getName() + ", " +
                                    requestPojo.getCity().getCountry());

                            // calculate and set temperature
                            int currentHour = (new Date()).getHours();
                            Date date = DataConverter.convertStringToDate(requestPojo.getList().get(0).getDtTxt());
                            int firstHour = date.getHours();
                            int firstTemperature = DataConverter.convertKelvinToCelsius(requestPojo.getList().get(0).getMain().getTemp());
                            date = DataConverter.convertStringToDate(requestPojo.getList().get(1).getDtTxt());
                            int secondHour = date.getHours();
                            int secondTemperature = DataConverter.convertKelvinToCelsius(requestPojo.getList().get(1).getMain().getTemp());
                            int currentTemperature = calculateCurrentTemperature(firstHour,
                                    firstTemperature, secondHour, secondTemperature, currentHour);
                            // log
                            /*
                            System.out.println("current hour: " + currentHour);
                            System.out.println("first hour: " + firstHour);
                            System.out.println("first temperature: " + firstTemperature);
                            System.out.println("second hour: " + secondHour);
                            System.out.println("second temperature: " + secondTemperature);
                            System.out.println("calculated current temperature: " + currentTemperature);
                            System.out.println(String.valueOf(currentTemperature));
                            */

                            mView.setNowTemperature(String.valueOf(currentTemperature) + "°C");
                            mView.setNowSky(requestPojo.getList().get(0).getWeather().get(0).getDescription());
                        },
                        Throwable::printStackTrace
                );

    }

    private int calculateCurrentTemperature(int firstHour, int firstTemperature, int secondHour, int secondTemperature, int currentHour) {
        int curentTemp = 0;
        if (currentHour == firstHour)
            curentTemp = firstTemperature;
        else {
            if (firstHour < secondHour) { // one day
                if (currentHour < firstHour) {
                    curentTemp = firstTemperature;
                } else {
                    if (currentHour > firstHour && currentHour > secondHour) {
                        curentTemp = firstTemperature;
                    } else {
                        double hoursDiff = (double) secondHour - (double) firstHour;
                        double tempDiff = (secondTemperature - (double) firstTemperature);
                        int firstVSCurrentHoursDiff = currentHour - firstHour;
                        double koefTempForHour = tempDiff / hoursDiff; // change temp by one hour
                        double changedTemp = firstVSCurrentHoursDiff * koefTempForHour;
                        curentTemp = (int) Math.round(
                                (double) firstTemperature + changedTemp
                        );
                        // log
                        /*
                        System.out.println("hoursDiff " + hoursDiff);
                        System.out.println("tempDiff " + tempDiff);
                        System.out.println("firstVSCurrentHoursDiff " + firstVSCurrentHoursDiff);
                        System.out.println("koefTempForHour " + koefTempForHour);
                        System.out.println("changedTemp: " + changedTemp);
                         */
                    }
                }
            } else if (firstHour > secondHour) { // two days
                if (currentHour < firstHour) {
                    curentTemp = firstTemperature;
                } else {
                    double hoursDiff = (double) secondHour + (24 - (double) firstHour);
                    double tempDiff = ((double) secondTemperature - (double) firstTemperature);
                    int firstVSCurrentHoursDiff;
                    if (currentHour > firstHour)  // currentHour in first day
                        firstVSCurrentHoursDiff = currentHour - firstHour;
                    else  // currentHour in second day
                        firstVSCurrentHoursDiff = currentHour + (24 - firstHour);
                    double koefTempForHour = tempDiff / hoursDiff; // change temp by one hour
                    double changedTemp = firstVSCurrentHoursDiff * koefTempForHour;
                    curentTemp = (int) Math.round(
                            (double) firstTemperature + changedTemp
                    );
                    // log
                    /*
                    System.out.println("hoursDiff " + hoursDiff);
                    System.out.println("tempDiff " + tempDiff);
                    System.out.println("firstVSCurrentHoursDiff " + firstVSCurrentHoursDiff);
                    System.out.println("koefTempForHour " + koefTempForHour);
                    System.out.println("changedTemp: " + changedTemp);
                    */
                }
            } else
                curentTemp = firstTemperature;
        }
        return curentTemp;
    }

    @Override
    public void onSearchButtonPressed() {
        setPlaceToPref();
        loadData();
        showData();
    }

    @Override
    public void setPlaceToPref() {
        AppPreferencesManager.setPlace(mView.getUserEnteredPlace());
    }

    @Override
    public String getPlaceFromPref() {
        return AppPreferencesManager.getPlace();
    }

    @Override
    public void releasePresenter() {
        if (mRequestPojoSubscription != null && !mRequestPojoSubscription.isUnsubscribed())
            mRequestPojoSubscription.unsubscribe();
        if (mShortDayWeatherPojoSubscription != null && !mShortDayWeatherPojoSubscription.isUnsubscribed())
            mShortDayWeatherPojoSubscription.unsubscribe();
        mView = null;
        mRequestPojoObservable = null;
    }
}
