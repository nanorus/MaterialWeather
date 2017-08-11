package com.example.nanorus.materialweather.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataConverter {

    @Inject
    public DataConverter(){

    }

    public  Date convertStringToDate(String string) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public  int convertKelvinToCelsius(double kelvin){
        return (int) Math.round(kelvin)-273;
    }

}
