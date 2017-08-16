package com.example.nanorus.materialweather.model;

import android.text.format.DateFormat;

import com.example.nanorus.materialweather.model.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.model.api.services.FiveDaysForecastService;
import com.example.nanorus.materialweather.model.database.DatabaseManager;
import com.example.nanorus.materialweather.model.pojo.CurrentTimeWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.current_time.CurrentRequestPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class DataManager {

    private DatabaseManager mDatabaseManager;
    private DataMapper mDataConverter;
    private CurrentTimeForecastService mCurrentTimeForecastService;
    private FiveDaysForecastService mFiveDaysForecastService;

    @Inject
    public DataManager(DataMapper dataConverter, CurrentTimeForecastService currentTimeForecastService,
                       FiveDaysForecastService fiveDaysForecastService, DatabaseManager databaseManager) {
        mDataConverter = dataConverter;
        mCurrentTimeForecastService = currentTimeForecastService;
        mFiveDaysForecastService = fiveDaysForecastService;
        mDatabaseManager = databaseManager;
    }

    public Observable<CurrentTimeWeatherPojo> getCurrentWeather(String place) {
        return getCurrentWeatherRequest(place)
                .map(currentRequestPojo -> new CurrentTimeWeatherPojo(
                        mDataConverter.kelvinToCelsius(currentRequestPojo.getMain().getTemp()),
                        currentRequestPojo.getWeather().get(0).getDescription(),
                        (int) currentRequestPojo.getMain().getPressure(),
                        currentRequestPojo.getMain().getHumidity(),
                        currentRequestPojo.getClouds().getAll(),
                        currentRequestPojo.getWind().getSpeed(),
                        currentRequestPojo.getCod(),
                        currentRequestPojo.getName() + ", " + currentRequestPojo.getSys().getCountry()
                ));
    }

    public Observable<CurrentRequestPojo> getCurrentWeatherRequest(String place) {
        return mCurrentTimeForecastService.getRequestPojoObservable(place);
    }

    /*
    public Observable<ThreeHoursWeatherPojo> getThreeHoursWeatherPojosByDay(String place, int day) {
        return getFullWeatherThreeHoursOnline(place)
                .filter(listPojo ->
                        Integer.parseInt((String) DateFormat.format("dd", mDataConverter.stringToDate(listPojo.getDtTxt())))
                                == day)
                .map(listPojo -> {
                    Date date = mDataConverter.stringToDate(listPojo.getDtTxt());
                    return new ThreeHoursWeatherPojo(
                            (int) Math.round(listPojo.getMain().getTemp()) - 273,
                            listPojo.getWeather().get(0).getDescription(),
                            (int) Math.round(listPojo.getMain().getPressure()),
                            (int) Math.round(listPojo.getMain().getHumidity()),
                            (int) listPojo.getClouds().getAll(),
                            listPojo.getWind().getSpeed(),
                            (int) Math.round(listPojo.getWind().getDeg()),
                            date.getHours(),
                            date.getMinutes()
                    );
                });

    }
    */

    public Observable<ShortDayWeatherPojo> getDaysWeatherOnline(String place) {
        return Observable.create(subscriber -> {
            ArrayList<Integer> temperaturesList = new ArrayList<>();
            final int[] previousDay = {0};
            final int[] dayOfMonth = {0};
            final int[] month = {0};
            final int[] dayOfWeek = {0};
            final int[] minTemp = {0};
            final int[] maxTemp = {0};
            getFullWeatherThreeHoursOnline(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(
                            fiveDaysListPojo -> {
                                Date date = mDataConverter.stringToDate(fiveDaysListPojo.getDtTxt());
                                Calendar c = Calendar.getInstance();
                                c.setTime(date);
                                int day = Integer.parseInt((String) DateFormat.format("dd", date));
                                if (previousDay[0] == 0) {  // start day
                                    previousDay[0] = day;
                                    temperaturesList.add(mDataConverter.kelvinToCelsius(fiveDaysListPojo.getMain().getTemp()));
                                    dayOfMonth[0] = day;
                                    month[0] = date.getMonth();
                                    dayOfWeek[0] = c.get(Calendar.DAY_OF_WEEK);
                                } else {
                                    if (previousDay[0] == day) {  // next in this day
                                        temperaturesList.add(mDataConverter.kelvinToCelsius(fiveDaysListPojo.getMain().getTemp()));
                                    } else {  // end of day
                                        minTemp[0] = temperaturesList.get(0);
                                        maxTemp[0] = temperaturesList.get(0);
                                        for (Integer i : temperaturesList) {
                                            if (i < minTemp[0]) minTemp[0] = i;
                                            if (i > maxTemp[0]) maxTemp[0] = i;
                                        }
                                        previousDay[0] = 0;
                                        temperaturesList.clear();

                                        subscriber.onNext(new ShortDayWeatherPojo(dayOfMonth[0], month[0],
                                                dayOfWeek[0], minTemp[0], maxTemp[0]));
                                    }
                                }
                            },

                            Throwable::printStackTrace,

                            () -> {
                                // end of the last day

                                minTemp[0] = temperaturesList.get(0);
                                maxTemp[0] = temperaturesList.get(0);
                                for (Integer i : temperaturesList) {
                                    if (i < minTemp[0]) minTemp[0] = i;
                                    if (i > maxTemp[0]) maxTemp[0] = i;
                                }

                                previousDay[0] = 0;
                                temperaturesList.clear();

                                subscriber.onNext(new ShortDayWeatherPojo(dayOfMonth[0], month[0],
                                        dayOfWeek[0], minTemp[0], maxTemp[0]));
                            }
                    );
        });
    }

    public Observable<ShortDayWeatherPojo> getDaysWeatherOffline() {
        return mDatabaseManager.getDaysWeather();
    }

    public Observable<FiveDaysRequestPojo> getFullWeatherOnline(String place) {
        return mFiveDaysForecastService.getRequestPojoObservable(place);
    }

    public Observable<FiveDaysListPojo> getFullWeatherThreeHoursOnline(String place) {
        // list for 3 hours each (ListPojo)
        return getFullWeatherOnline(place)
                .map(requestPojo -> (requestPojo.getList()))
                .flatMap(Observable::from);
    }

    public void putFullWeatherData(FiveDaysRequestPojo data) {
        Completable.create(completableSubscriber -> {
            mDatabaseManager.putFullWeatherData(data);
            completableSubscriber.onCompleted();
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        }, Throwable::printStackTrace
                );
    }

}
