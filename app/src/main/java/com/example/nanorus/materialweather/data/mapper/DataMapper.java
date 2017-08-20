package com.example.nanorus.materialweather.data.mapper;

import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ThreeHoursWeatherPojo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Emitter;
import rx.Observable;

@Singleton
public class DataMapper {

    @Inject
    public DataMapper() {

    }

    public Date stringToDate(String string) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public int kelvinToCelsius(double kelvin) {
        return (int) Math.round(kelvin) - 273;
    }

    public Observable<ShortDayWeatherPojo> threeHoursWeatherPojoToShortDayWeatherPojoObservable(List<ThreeHoursWeatherPojo> threeHoursWeatherPojos) {
        return Observable.create(
                subscriber -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(threeHoursWeatherPojos.get(0).getDate());
                    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    ArrayList<ArrayList<ThreeHoursWeatherPojo>> groupedThreeHoursWeatherPojosByDays = new ArrayList<>();
                    ArrayList<ThreeHoursWeatherPojo> currentGroupOfHours = new ArrayList<>();

                    // grouping hours by days
                    for (int i = 0; i < threeHoursWeatherPojos.size(); i++) {
                        ThreeHoursWeatherPojo currentHour = threeHoursWeatherPojos.get(i);
                        calendar.setTime(currentHour.getDate());
                        if (currentDay != calendar.get(Calendar.DAY_OF_MONTH)) {
                            // new day
                            groupedThreeHoursWeatherPojosByDays.add(currentGroupOfHours);
                            currentGroupOfHours = new ArrayList<>();
                            calendar.setTime(currentHour.getDate());
                            currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                        } else {
                            // old day
                            currentGroupOfHours.add(threeHoursWeatherPojos.get(i));
                        }
                    }
                    // last day for grouping
                    groupedThreeHoursWeatherPojosByDays.add(currentGroupOfHours);
                    currentGroupOfHours = new ArrayList<>();
                    // grouping completed

                    // converting
                    for (int i = 0; i < groupedThreeHoursWeatherPojosByDays.size(); i++) {
                        ArrayList<Integer> temperatures = new ArrayList<>();
                        Date currentDate = groupedThreeHoursWeatherPojosByDays.get(i).get(0).getDate();
                        for (int j = 0; j < groupedThreeHoursWeatherPojosByDays.get(i).size(); j++) {
                            temperatures.add(groupedThreeHoursWeatherPojosByDays.get(i).get(j).getTemp());
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(currentDate);
                        // send day
                        subscriber.onNext(new ShortDayWeatherPojo(
                                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_WEEK),
                                Collections.min(temperatures),
                                Collections.max(temperatures)));
                    }
                    // converting completed
                    subscriber.onCompleted();
                }, Emitter.BackpressureMode.BUFFER);
    }

}
