package com.example.nanorus.materialweather.presentation.ui;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    private Context mContext;

    public Toaster(Context context) {
        mContext = context;
    }

    public void shortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void shortToast(int resTextId) {
        Toast.makeText(mContext, mContext.getText(resTextId), Toast.LENGTH_SHORT).show();
    }

}
