package com.example.nanorus.materialweather.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nanorus.materialweather.data.mapper.DataMapper;
import com.example.nanorus.materialweather.data.entity.FullDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.data.entity.ThreeHoursWeatherPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.five_days.FiveDaysListPojo;
import com.example.nanorus.materialweather.data.entity.forecast.api.five_days.FiveDaysRequestPojo;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Emitter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseManager {

    private DatabaseHelper mDatabaseHelper;
    private DatabaseContract mDatabaseContract;
    private Subscription mDaysWeatherSubscription;

    @Inject
    public DatabaseManager(DatabaseHelper databaseHelper, DatabaseContract databaseContract) {
        mDatabaseHelper = databaseHelper;
        mDatabaseContract = databaseContract;
    }

    public Observable<FullDayWeatherPojo> getSingleDayWeather(int id) {
        // cursor, query
        return null;
    }

    public Observable<ShortDayWeatherPojo> getDaysWeather() {
        return Observable.create(
                subscriber -> {
                    SQLiteDatabase db = mDatabaseHelper.getReadableDB();

                    Cursor cursorAllData = db.rawQuery("SELECT * FROM " + mDatabaseContract.TABLE_NAME_WEATHER, null);

                    System.out.println("databaseManager: READ FROM DB");
                    // list of hours weather from db
                    ArrayList<ThreeHoursWeatherPojo> threeHoursWeatherPojos = new ArrayList<>();
                    if (cursorAllData.moveToFirst()) {
                        do {
                            threeHoursWeatherPojos.add(new ThreeHoursWeatherPojo(
                                    DataMapper.kelvinToCelsius(cursorAllData.getDouble(
                                            cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_TEMPERATURE)
                                    )),
                                    cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DESCRIPTION)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_PRESSURE)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_HUMIDITY)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_CLOUDINESS)),
                                    cursorAllData.getDouble(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_SPEED)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_DIRECTION)),
                                    DataMapper.stringToDate(cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DATE)))
                            ));
                        } while (cursorAllData.moveToNext());
                    }
                    cursorAllData.close();
                    mDatabaseHelper.closeDB();
                    // mapping list of hours weather to days weather observable
                    Observable<ShortDayWeatherPojo> daysWeatherObservable =
                            DataMapper.threeHoursWeatherPojoToShortDayWeatherPojoObservable(threeHoursWeatherPojos);
                    mDaysWeatherSubscription = daysWeatherObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    subscriber::onNext,
                                    subscriber::onError,
                                    subscriber::onCompleted
                            );

                },
                Emitter.BackpressureMode.BUFFER
        );
    }

    public void putFullWeatherData(FiveDaysRequestPojo data) {
        System.out.println("databaseManager: WRITE TO DB");
        SQLiteDatabase db = mDatabaseHelper.getWritableDB();
        FiveDaysListPojo list;
        ContentValues cv = new ContentValues();
        db.delete(mDatabaseContract.TABLE_NAME_WEATHER, null, null);
        for (int i = 0; i < data.getList().size(); i++) {
            list = data.getList().get(i);
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
        mDatabaseHelper.closeDB();
    }

    public void releaseDatabaseManager() {
        mDaysWeatherSubscription.unsubscribe();
        mDaysWeatherSubscription = null;
    }

}
