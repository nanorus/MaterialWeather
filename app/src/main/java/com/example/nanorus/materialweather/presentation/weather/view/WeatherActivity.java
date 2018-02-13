package com.example.nanorus.materialweather.presentation.weather.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.presentation.ui.adapters.ForecastRecyclerViewAdapter;
import com.example.nanorus.materialweather.presentation.weather.presenter.IWeatherActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements IWeatherActivity {

    @Inject
    IWeatherActivityPresenter mPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.weather_et_place)
    EditText placeEditText;
    @BindView(R.id.weather_iv_search)
    ImageView searchImageView;
    @BindView(R.id.weather_tv_place)
    TextView placeTextView;
    @BindView(R.id.weather_rv_weatherList)
    RecyclerView weatherRecyclerView;
    @BindView(R.id.weather_now_tv_sky)
    TextView skyTextView;
    @BindView(R.id.weather_now_tv_temperature)
    TextView temperatureTextView;

    private ForecastRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ShortDayWeatherPojo> mWeatherDaysList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        setupNavigationDrawer();
        
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
        weatherRecyclerView.setAdapter(mAdapter);
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            weatherRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void setNowTemperature(String temperature) {
        temperatureTextView.setText(temperature);
    }

    @Override
    public void setNowSky(String sky) {
        skyTextView.setText(sky);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserEnteredPlace(String place) {
        try {
            placeEditText.setText(place);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getUserEnteredPlace() {
        return placeEditText.getText().toString();
    }

    @Override
    public void setWebPlace(String place) {
        placeTextView.setText(place);
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
        searchImageView.setOnClickListener(view -> mPresenter.onSearchButtonPressed());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupNavigationDrawer() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("xaxa");
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
    }
}
