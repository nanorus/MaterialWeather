package com.example.nanorus.materialweather.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;
import com.example.nanorus.materialweather.presenter.IWeatherPresenter;
import com.example.nanorus.materialweather.presenter.WeatherPresenter;
import com.example.nanorus.materialweather.view.ui.adapters.ForecastRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements IWeatherActivity {
    private IWeatherPresenter mPresenter;

    private EditText weather_et_place;
    private ImageView weather_iv_search;
    private TextView weather_tv_place;
    private RecyclerView weather_rv_weatherList;

    private ForecastRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ListPojo> mWeatherList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weather_et_place = (EditText) findViewById(R.id.weather_et_place);
        weather_iv_search = (ImageView) findViewById(R.id.weather_iv_search);
        weather_tv_place = (TextView) findViewById(R.id.weather_tv_place);
        weather_rv_weatherList = (RecyclerView) findViewById(R.id.weather_rv_weatherList);

        mPresenter = new WeatherPresenter(getView());


        setListeners();
    }


    @Override
    protected void onDestroy() {
        mPresenter.releasePresenter();
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void createWeatherList() {
        if (mWeatherList != null)
            mWeatherList.clear();
        mWeatherList = new ArrayList<>();
    }

    @Override
    public void addToWeatherList(ListPojo listPojo) {
        mWeatherList.add(listPojo);
    }

    @Override
    public void setAdapter() {
        if (mAdapter != null) {
            mAdapter = null;
        }
        mAdapter = new ForecastRecyclerViewAdapter(mWeatherList);
        weather_rv_weatherList.setAdapter(mAdapter);

        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            weather_rv_weatherList.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void updateAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
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
        weather_iv_search.setOnClickListener(view -> {
            mPresenter.onSearchButtonPressed();
        });
    }

}
