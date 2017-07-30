package com.example.nanorus.materialweather.model;

import android.text.format.DateFormat;

import com.example.nanorus.materialweather.model.api.WeatherRetroClient;
import com.example.nanorus.materialweather.model.api.services.ForecastService;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ThreeHoursWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.ListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.RequestPojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DataManager {


    public static Observable<ThreeHoursWeatherPojo> getThreeHoursWeatherPojosByDay(String place, int day) {
        Observable<ThreeHoursWeatherPojo> threeHoursWeatherPojoObservableOneDay = get3hForecastData(place)
                .filter(listPojo ->
                        Integer.parseInt((String) DateFormat.format("dd", DataConverter.convertStringToDate(listPojo.getDtTxt())))
                                == day)
                .map(listPojo -> {
                    Date date = DataConverter.convertStringToDate(listPojo.getDtTxt());
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
        return threeHoursWeatherPojoObservableOneDay;

    }

    public static Observable<ShortDayWeatherPojo> getShortDayWeatherPojos(String place) {
        Observable<ShortDayWeatherPojo> shortDayWeatherPojoObservable = Observable.create(subscriber -> {
            ArrayList<Integer> temperaturesList = new ArrayList<>();
            final int[] previousDay = {0};
            final int[] dayOfMonth = {0};
            final int[] month = {0};
            final int[] dayOfWeek = {0};
            final int[] minTemp = {0};
            final int[] maxTemp = {0};
            get3hForecastData(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Subscriber<ListPojo>() {
                @Override
                public void onCompleted() {
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

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(ListPojo listPojo) {
                    Date date = DataConverter.convertStringToDate(listPojo.getDtTxt());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int day = Integer.parseInt((String) DateFormat.format("dd", date));
                    if (previousDay[0] == 0) {  // start day

                        previousDay[0] = day;
                        temperaturesList.add(DataConverter.convertKelvinToCelsius(listPojo.getMain().getTemp()));

                        dayOfMonth[0] = day;
                        month[0] = date.getMonth();
                        dayOfWeek[0] = c.get(Calendar.DAY_OF_WEEK);
                    } else {
                        if (previousDay[0] == day) {  // next in this day

                            temperaturesList.add(DataConverter.convertKelvinToCelsius(listPojo.getMain().getTemp()));
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
                }
            });
        });
        return shortDayWeatherPojoObservable;
    }


    public static Observable<RequestPojo> getFullForecastData(String place) {
        Retrofit retrofit = WeatherRetroClient.getInstance();
        ForecastService service = retrofit.create(ForecastService.class);

        // all data (RequestPojo)
        Observable<RequestPojo> requestPojoObservable = service.getRequestPojoObservable(place);
        return requestPojoObservable;
    }

    public static Observable<ListPojo> get3hForecastData(String place) {
        // list for 3 hours each (ListPojo)
        Observable<ListPojo> listPojoObservable = getFullForecastData(place)
                .map(requestPojo -> (requestPojo.getList()))
                .flatMap(listPojos -> Observable.from(listPojos));
        return listPojoObservable;
    }


}
