package com.example.nanorus.materialweather.data.weather;

import com.example.nanorus.materialweather.data.AppPreferencesManager;
import com.example.nanorus.materialweather.data.api.services.CurrentTimeForecastService;
import com.example.nanorus.materialweather.data.api.services.FiveDaysForecastService;
import com.example.nanorus.materialweather.data.database.DatabaseManager;
import com.example.nanorus.materialweather.data.entity.NowWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.current_time.CurrentRequestPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.five_days.FiveDaysRequestPojo;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

public class WeatherRepository {

    private DatabaseManager mDatabaseManager;
    private CurrentTimeForecastService mCurrentTimeForecastService;
    private FiveDaysForecastService mFiveDaysForecastService;
    private AppPreferencesManager mAppPreferencesManager;

    @Inject
    public WeatherRepository(CurrentTimeForecastService currentTimeForecastService,
                             FiveDaysForecastService fiveDaysForecastService, DatabaseManager databaseManager,
                             AppPreferencesManager appPreferencesManager) {
        mCurrentTimeForecastService = currentTimeForecastService;
        mFiveDaysForecastService = fiveDaysForecastService;
        mDatabaseManager = databaseManager;
        mAppPreferencesManager = appPreferencesManager;
    }

    public Single<NowWeatherPojo> getNowWeatherOnline(String place) {
        return getNowWeatherRequestOnline(place)
                .map(NowWeatherPojo::map);
    }

    public Single<CurrentRequestPojo> getNowWeatherRequestOnline(String place) {
        return mCurrentTimeForecastService.getRequestPojoObservable(place);
    }

    /*
    public Observable<ThreeHoursWeatherPojo> getThreeHoursWeatherPojosByDay(String place, int day) {
        return loadFullWeatherThreeHoursOnline(place)
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

    /*   public Observable<ShortDayWeatherPojo> getDaysWeatherOnline(String place) {
           return Observable.create(subscriber -> {
               ArrayList<Integer> temperaturesList = new ArrayList<>();
               final int[] previousDay = {0};
               final int[] dayOfMonth = {0};
               final int[] month = {0};
               final int[] dayOfWeek = {0};
               final int[] minTemp = {0};
               final int[] maxTemp = {0};
               loadFullWeatherThreeHoursOnline(place)
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
   */
    public Observable<ShortDayWeatherPojo> getDaysWeatherOffline() {
        return mDatabaseManager.getDaysWeather();
    }

    public Single<FiveDaysRequestPojo> getFiveDaysWeatherOnline(String place) {
        return mFiveDaysForecastService.getRequestPojoSingle(place);
    }

/*    public Observable<FiveDaysListPojo> loadFullWeatherThreeHoursOnline(String place) {
        // list for 3 hours each (ListPojo)
        return getFiveDaysWeatherOnline(place)
                .map(requestPojo -> (requestPojo.getList()))
                .flatMap(Observable::from);
    }*/

    public void saveFullWeatherData(FiveDaysRequestPojo data) {
        mDatabaseManager.putFullWeatherData(data);
    }

    public void saveNowWeatherData(NowWeatherPojo currentTimeWeatherPojo) {
        mAppPreferencesManager.setNowWeatherData(currentTimeWeatherPojo);
    }

    public Single<NowWeatherPojo> loadNowWeatherData() {
        return mAppPreferencesManager.getNowWeatherData();
    }

}
