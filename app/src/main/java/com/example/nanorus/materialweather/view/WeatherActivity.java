package com.example.nanorus.materialweather.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.presenter.WeatherPresenter;

public class WeatherActivity extends AppCompatActivity implements WeatherInterface.View {
    private WeatherInterface.Action mPresenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mPresenter = new WeatherPresenter(getView());

    }


    @Override
    protected void onDestroy() {
        mPresenter.releasePresenter();
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public WeatherInterface.View getView() {
        return this;
    }
}
