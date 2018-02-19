package com.example.nanorus.materialweather.presentation.weather.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.data.entity.ShortDayWeatherPojo;
import com.example.nanorus.materialweather.presentation.ui.adapters.ForecastRecyclerViewAdapter;
import com.example.nanorus.materialweather.presentation.weather.presenter.IWeatherPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements IWeatherActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    IWeatherPresenter mPresenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    ConstraintLayout mNavigationHeader;
    ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.weather_tv_place)
    TextView mPlaceTextView;
    @BindView(R.id.weather_rv_weatherList)
    RecyclerView mWeatherRecyclerView;
    @BindView(R.id.weather_now_tv_sky)
    TextView mSkyTextView;
    @BindView(R.id.weather_now_tv_temperature)
    TextView mTemperatureTextView;

    private ForecastRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ShortDayWeatherPojo> mWeatherDaysList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.weather));

        setupNavigationDrawer();

        App.getApp().getWeatherComponent().inject(this);
        mPresenter.bindView(getView());
        mPresenter.startWork();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResumeView(mPlaceTextView.getText().toString());
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
        mWeatherRecyclerView.setAdapter(mAdapter);
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mWeatherRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

    @Override
    public void setNowTemperature(String temperature) {
        mTemperatureTextView.setText(temperature);
    }

    @Override
    public void setNowSky(String sky) {
        mSkyTextView.setText(sky);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setWebPlace(String place) {
        mPlaceTextView.setText(place);
    }

    @Override
    public IWeatherActivity getView() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
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

    @Override
    public void closeDrawer() {
        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void setupNavigationDrawer() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationHeader = (ConstraintLayout) mNavigationView.getHeaderView(0);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getString(R.string.weather));
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getString(R.string.menu));
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_item_settings:
                    mPresenter.onSettingsClick();
                    break;
            }
            return false;
        });
        mNavigationHeader.findViewById(R.id.button_menu_changeCity).setOnClickListener(view -> mPresenter.onSettingsClick());
    }


}
