package com.example.nanorus.materialweather.data;

import android.content.Context;

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
}
