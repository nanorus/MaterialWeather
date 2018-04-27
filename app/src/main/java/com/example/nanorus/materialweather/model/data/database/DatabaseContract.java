package com.example.nanorus.materialweather.model.data.database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseContract {

    @Inject
    public DatabaseContract() {
    }

    public final String TABLE_NAME_WEATHER = "weather";

    public final String COLUMN_NAME_WEATHER_TEMPERATURE = "temperature";
    public final String COLUMN_NAME_WEATHER_DESCRIPTION = "description";
    public final String COLUMN_NAME_WEATHER_PRESSURE = "pressure";
    public final String COLUMN_NAME_WEATHER_HUMIDITY = "humidity";
    public final String COLUMN_NAME_WEATHER_CLOUDINESS = "cloudiness";
    public final String COLUMN_NAME_WEATHER_WIND_SPEED = "wind_speed";
    public final String COLUMN_NAME_WEATHER_WIND_DIRECTION = "wind_direction";
    public final String COLUMN_NAME_WEATHER_DATE = "date";
    public final String COLUMN_NAME_WEATHER_ICON = "icon";

    public final String COLUMN_NAME_ID = "_id";
    public final String COMMA = ",";

    public final String SQL_CREATE_TABLE_WEATHER = "CREATE TABLE " + TABLE_NAME_WEATHER + " (" +
            COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA +
            COLUMN_NAME_WEATHER_TEMPERATURE + " DOUBLE" + COMMA +
            COLUMN_NAME_WEATHER_DESCRIPTION + " TEXT" + COMMA +
            COLUMN_NAME_WEATHER_PRESSURE + " DOUBLE" + COMMA +
            COLUMN_NAME_WEATHER_HUMIDITY + " INTEGER" + COMMA +
            COLUMN_NAME_WEATHER_CLOUDINESS + " INTEGER" + COMMA +
            COLUMN_NAME_WEATHER_WIND_SPEED + " DOUBLE" + COMMA +
            COLUMN_NAME_WEATHER_WIND_DIRECTION + " DOUBLE" + COMMA +
            COLUMN_NAME_WEATHER_DATE + " TEXT" + COMMA +
            COLUMN_NAME_WEATHER_ICON + " TEXT" +
            ")";

    public final String SQL_DELETE_TABLE_WEATHER = "DROP TABLE IF EXISTS " + TABLE_NAME_WEATHER;

}
