package com.example.nanorus.materialweather.model.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.entity.weather.repository.HourForecast;
import com.example.nanorus.materialweather.entity.weather.repository.WeekForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.data.TempUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;
import rx.Subscription;

@Singleton
public class DatabaseManager {

    private final String TAG = this.getClass().getName();
    private DatabaseHelper mDatabaseHelper;
    private DatabaseContract mDatabaseContract;
    private Subscription mDaysWeatherSubscription;

    @Inject
    public DatabaseManager(DatabaseHelper databaseHelper, DatabaseContract databaseContract) {
        mDatabaseHelper = databaseHelper;
        mDatabaseContract = databaseContract;
    }

    public Single<WeekForecast> getWeekForecast() {
        Log.d(TAG, "getWeekForecast()");
        return Single.create(
                singleSubscriber -> {
                    SQLiteDatabase db = mDatabaseHelper.getReadableDB();

                    Cursor cursorAllData = db.rawQuery("SELECT * FROM " + mDatabaseContract.TABLE_NAME_WEATHER, null);
                    ArrayList<HourForecast> hourForecasts = new ArrayList<>();
                    if (cursorAllData.moveToFirst()) {
                        do {
                            hourForecasts.add(new HourForecast(
                                    TempUtils.kelvinToCelsius(cursorAllData.getDouble(
                                            cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_TEMPERATURE)
                                    )),
                                    cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DESCRIPTION)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_PRESSURE)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_HUMIDITY)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_CLOUDINESS)),
                                    cursorAllData.getDouble(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_SPEED)),
                                    cursorAllData.getInt(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_DIRECTION)),
                                    DateUtils.dtTxtToDate(cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_DATE))),
                                    cursorAllData.getString(cursorAllData.getColumnIndex(mDatabaseContract.COLUMN_NAME_WEATHER_ICON))
                            ));
                        } while (cursorAllData.moveToNext());
                    }
                    cursorAllData.close();
                    mDatabaseHelper.closeDB();

                    singleSubscriber.onSuccess(new WeekForecast(DayForecast.fromHourForecasts(hourForecasts)));
                });
    }

    public void putWeekForecast(WeekForecast data) {
        Log.d(TAG, "putWeekForecast()");
        SQLiteDatabase db = mDatabaseHelper.getWritableDB();
        List<HourForecast> hourForecasts = data.getHourForecasts();
        ContentValues cv = new ContentValues();
        db.delete(mDatabaseContract.TABLE_NAME_WEATHER, null, null);
        for (HourForecast hourForecast : hourForecasts) {
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_DATE, DateUtils.dateToDtTxt(hourForecast.getDate()));
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_TEMPERATURE, TempUtils.celsiusToKelvin(hourForecast.getTemp()));
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_DESCRIPTION, hourForecast.getDescription());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_CLOUDINESS, hourForecast.getCloudiness());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_PRESSURE, hourForecast.getPressure());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_HUMIDITY, hourForecast.getHumidity());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_SPEED, hourForecast.getWindSpeed());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_WIND_DIRECTION, hourForecast.getWindDirection());
            cv.put(mDatabaseContract.COLUMN_NAME_WEATHER_ICON, hourForecast.getIcon());

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
