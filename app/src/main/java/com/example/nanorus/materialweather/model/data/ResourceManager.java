package com.example.nanorus.materialweather.model.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nanorus.materialweather.R;

public class ResourceManager {

    Context mContext;

    public ResourceManager(Context context) {
        mContext = context;
    }

    public String enterLocalityText() {
        return mContext.getString(R.string.enter_locality);
    }

    public String cityNotFound() {
        return mContext.getString(R.string.city_not_found);
    }

    public String apiPlaceNotFound(){
        return mContext.getString(R.string.api_place_not_found);
    }

    public String networkError() {
        return mContext.getString(R.string.network_error);
    }

    public Bitmap getWeatherIcon(String iconName){
        int resource = R.drawable.ic_w_01d;
        switch (iconName){
            case "01d":
                resource = R.drawable.ic_w_01n;
                break;
            case "01n":
                resource = R.drawable.ic_w_01n;
                break;
            case "02d":
                resource = R.drawable.ic_w_02d;
                break;
            case "02n":
                resource = R.drawable.ic_w_02n;
                break;
            case "03d":
                resource = R.drawable.ic_w_03d;
                break;
            case "03n":
                resource = R.drawable.ic_w_03n;
                break;
            case "04d":
                resource = R.drawable.ic_w_04d;
                break;
            case "04n":
                resource = R.drawable.ic_w_04n;
                break;
            case "09d":
                resource = R.drawable.ic_w_09d;
                break;
            case "09n":
                resource = R.drawable.ic_w_09n;
                break;
            case "10d":
                resource = R.drawable.ic_w_10d;
                break;
            case "10n":
                resource = R.drawable.ic_w_10n;
                break;
            case "11d":
                resource = R.drawable.ic_w_11d;
                break;
            case "11n":
                resource = R.drawable.ic_w_11n;
                break;
            case "13d":
                resource = R.drawable.ic_w_13d;
                break;
            case "13n":
                resource = R.drawable.ic_w_13n;
                break;
            case "50d":
                resource = R.drawable.ic_w_50d;
                break;
            case "50n":
                resource = R.drawable.ic_w_50n;
                break;
        }
        return BitmapFactory.decodeResource(mContext.getResources(), resource);
    }
}
