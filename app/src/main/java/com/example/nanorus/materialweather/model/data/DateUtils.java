package com.example.nanorus.materialweather.model.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static long secondsPassed(Date date){
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / 1000 % 60;
    }
    public static long minutesPassed(Date date){
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / (60 * 1000) % 60;
    }

    public static long hoursPassed(Date date){
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / (60 * 60 * 1000) % 24;
    }
    public static long daysPassed(Date date){
        long diff = System.currentTimeMillis() - date.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }


    public static Date dtTxtToDate(String string) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, HH:mm", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String dateToDtTxt(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String getDayString(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        if (day.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            return "Today, " + (new SimpleDateFormat("dd MMMM", Locale.ENGLISH)).format(date);
        } else {
            return (new SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH)).format(date);
        }
    }

}
