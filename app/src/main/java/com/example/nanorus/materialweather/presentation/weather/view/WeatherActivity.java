package com.example.nanorus.materialweather.presentation.weather.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.presentation.weather.presenter.IWeatherActivityPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.ForecastRecyclerViewAdapter;

import java.util.List;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity implements IWeatherActivity {

    @Inject
    IWeatherActivityPresenter mPresenter;

    private EditText weather_et_place;
    private ImageView weather_iv_search;
    private TextView weather_tv_place;
    private RecyclerView weather_rv_weatherList;
    private TextView weather_now_tv_sky;
    private TextView weather_now_tv_temperature;

    private ForecastRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ShortDayWeatherPojo> mWeatherDaysList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weather_et_place = (EditText) findViewById(R.id.weather_et_place);
        weather_iv_search = (ImageView) findViewById(R.id.weather_iv_search);
        weather_tv_place = (TextView) findViewById(R.id.weather_tv_place);
        weather_rv_weatherList = (RecyclerView) findViewById(R.id.weather_rv_weatherList);
        weather_now_tv_sky = (TextView) findViewById(R.id.weather_now_tv_sky);
        weather_now_tv_temperature = (TextView) findViewById(R.id.weather_now_tv_temperature);

        App.getApp().getWeatherComponent().inject(this);
        mPresenter.bindView(getView());
        mPresenter.startWork();

        setListeners();
    }


    @Override
    protected void onDestroy() {
        mPresenter.releasePresenter();
        mPresenter = null;
        App.getApp().releaseWeatherComponent();
        super.onDestroy();
    }

    @Override
    public void createWeatherList(List<ShortDayWeatherPojo> weatherDaysList) {
            mWeatherDaysList = weatherDaysList;
    }


    @Override
    public void setAdapter() {
        mAdapter = new ForecastRecyclerViewAdapter(mWeatherDaysList);
        weather_rv_weatherList.setAdapter(mAdapter);
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            weather_rv_weatherList.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void setNowTemperature(String temperature) {
        weather_now_tv_temperature.setText(temperature);
    }

    @Override
    public void setNowSky(String sky) {
        weather_now_tv_sky.setText(sky);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserEnteredPlace(String place) {
        try {
            weather_et_place.setText(place);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserEnteredPlace() {
        return weather_et_place.getText().toString();
    }

    @Override
    public void setWebPlace(String place) {
        weather_tv_place.setText(place);
    }

    @Override
    public IWeatherActivity getView() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private void setListeners() {
        weather_iv_search.setOnClickListener(view -> mPresenter.onSearchButtonPressed());
    }

}
