package com.example.nanorus.materialweather.data;

public class Utils {

    public static boolean checkNetWorkError(Throwable throwable) {
        return false;
    }

    public static boolean check404Error(Throwable throwable) {
        return throwable.toString().contains("HTTP 404 Not Found");
    }

}
