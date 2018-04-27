package com.example.nanorus.materialweather.model.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseContract mDatabaseContract;

    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 4;

    public static int DBConnectionsCount = 0;

    public SQLiteDatabase getReadableDB() {
        DBConnectionsCount++;
        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDB() {
        DBConnectionsCount++;
        return this.getWritableDatabase();
    }

    public void closeDB() {
        DBConnectionsCount--;
        if (DBConnectionsCount == 0)
            this.close();
    }

    @Inject
    public DatabaseHelper(Context context, DatabaseContract databaseContract) {
        super(context, DB_NAME, null, DB_VERSION);
        mDatabaseContract = databaseContract;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(mDatabaseContract.SQL_CREATE_TABLE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == 2 && i1 == 2) {
            sqLiteDatabase.execSQL("ALTER TABLE " + mDatabaseContract.TABLE_NAME_WEATHER + " ADD COLUMN " + mDatabaseContract.COLUMN_NAME_WEATHER_ICON + " TEXT");
            sqLiteDatabase.execSQL("UPDATE " + mDatabaseContract.TABLE_NAME_WEATHER + " SET " + mDatabaseContract.COLUMN_NAME_WEATHER_ICON + " = 01d");
        }
        if (i == 3 || i == 4) {
            sqLiteDatabase.execSQL(mDatabaseContract.SQL_DELETE_TABLE_WEATHER);
            sqLiteDatabase.execSQL(mDatabaseContract.SQL_CREATE_TABLE_WEATHER);
        }
    }
}
