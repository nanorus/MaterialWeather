package com.example.nanorus.materialweather.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseContract mDatabaseContract;

    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 1;

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

    }
}
