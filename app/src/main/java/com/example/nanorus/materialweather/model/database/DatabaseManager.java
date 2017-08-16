package com.example.nanorus.materialweather.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nanorus.materialweather.model.DataMapper;
import com.example.nanorus.materialweather.model.pojo.FullDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.ThreeHoursWeatherPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysListPojo;
import com.example.nanorus.materialweather.model.pojo.forecast.api.five_days.FiveDaysRequestPojo;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Emitter;
import rx.Observable;
import rx.Subscription;

@Singleton
public class DatabaseManager {

    private DatabaseHelper mDatabaseHelper;
    private DatabaseContract mDatabaseContract;
    private DataMapper mDataMapper;
    private Subscription getDaysWeatherSubscription;

    @Inject
    public DatabaseManager(DatabaseHelper databaseHelper, DatabaseContract databaseContract,
                           DataMapper dataMapper) {
        mDatabaseHelper = databaseHelper;
        mDatabaseContract = databaseContract;
        mDataMapper = dataMapper;
    }

    public Observable<FullDayWeatherPojo> getSingleDayWeather(int id) {
        // cursor, query
        return null;
    }

    public Observable<ShortDayWeatherPojo> getDaysWeather() {
        return Observable.create(
                subscriber -> {
                    SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

                    Cursor cursorAllData = db.rawQuery("SELECT * FROM " + mDatabaseContract.TABLE_NAME_WEATHER, null);

                    // list of hours weather from db
                    ArrayList<ThreeHoursWeatherPojo> threeHoursWeatherPojos = new ArrayList<>();
                    if (cursorAllData.moveToFirst()) {
                        do {
                            System.out.println("databaseManager: new pojo");
                            threeHoursWeatherPojos.add(new ThreeHoursWeatherPojo(
                                    mDataMapper.kelvinToCelsius(cursorAllData.getDouble(
                                            cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_TEMPERATURE)
                                    )),
                                    cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DESCRIPTION)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_PRESSURE)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_HUMIDITY)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_CLOUDINESS)),
                                    cursorAllData.getDouble(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_SPEED)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_DIRECTION)),
                                    mDataMapper.stringToDate(cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DATE)))
                            ));
                        } while (cursorAllData.moveToNext());
                    }
                    cursorAllData.close();
                    System.out.println("databaseManager: getDaysWeather()");
                    System.out.println("databaseManager: threeHoursWeatherPojos: size: " + threeHoursWeatherPojos.size());
                    // mapping list of hours weather to days weather observable
                    Observable<ShortDayWeatherPojo> shortDayWeatherPojoObservable =
                            mDataMapper.threeHoursWeatherPojoToShortDayWeatherPojoObservable(
                                    threeHoursWeatherPojos
                            );
                    getDaysWeatherSubscription = shortDayWeatherPojoObservable.subscribe(
                            shortDayWeatherPojo -> {
                                subscriber.onNext(shortDayWeatherPojo);
                                System.out.println("databaseManager: getDaysWeather: temp: " +
                                        shortDayWeatherPojo.getMinTemp() + " - " +
                                        shortDayWeatherPojo.getMaxTemp()
                                );
                                System.out.println("databaseManager: getDaysWeather: onNext");
                            },
                            throwable -> {
                                subscriber.onError(throwable);
                                //getDaysWeatherSubscription.unsubscribe();
                            },
                            subscriber::onCompleted
                    );

                },
                Emitter.BackpressureMode.BUFFER
        );
    }

    public void putFullWeatherData(FiveDaysRequestPojo data) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        FiveDaysListPojo list;
        ContentValues cv = new ContentValues();
        db.delete(mDatabaseContract.TABLE_NAME_WEATHER, null, null);
        for (int i = 0; i < data.getList().size(); i++) {
            list = data.getList().get(i);
            System.out.println(list.getDtTxt());

            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_DATE, list.getDtTxt());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_TEMPERATURE, list.getMain().getTemp());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_DESCRIPTION, list.getWeather().get(0).getDescription());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_CLOUDINESS, list.getClouds().getAll());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_PRESSURE, list.getMain().getPressure());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_HUMIDITY, list.getMain().getHumidity());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_SPEED, list.getWind().getSpeed());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_DIRECTION, list.getWind().getDeg());

            db.insert(mDatabaseContract.TABLE_NAME_WEATHER, null, cv);
            cv.clear();
        }
    }

    public void releaseDatabaseManager() {
        getDaysWeatherSubscription.unsubscribe();
        getDaysWeatherSubscription = null;
    }

}
