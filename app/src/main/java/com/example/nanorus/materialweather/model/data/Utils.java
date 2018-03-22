package com.example.nanorus.materialweather.model.data;

public class Utils {

    public static boolean checkNetWorkError(Throwable throwable) {
        return throwable.toString().contains("Unable to resolve host");
    }

    public static boolean check404Error(Throwable throwable) {
        return throwable.toString().contains("HTTP 404 Not Found");
    }

}
