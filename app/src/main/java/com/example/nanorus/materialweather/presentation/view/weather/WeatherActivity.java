package com.example.nanorus.materialweather.presentation.view.weather;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.entity.weather.repository.CurrentWeather;
import com.example.nanorus.materialweather.entity.weather.repository.WeatherForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.presentation.presenter.weather.IWeatherPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.ForecastRecyclerViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements IWeatherActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    IWeatherPresenter mPresenter;
    @Inject
    ResourceManager mResourceManager;

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
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.textView_last_update_value)
    TextView mLastWeatherUpdateTextView;
    @BindView(R.id.imageView_weather_icon)
    ImageView mWeatherIcon;

    private ForecastRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getString(R.string.weather));

        setupNavigationDrawer();

        App.getApp().getWeatherComponent().inject(this);
        mPresenter.bindView(getView());
        mPresenter.startWork();
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onRefresh());
        mSwipeRefresh.setColorSchemeResources(R.color.swipe_refresh_color_1, R.color.swipe_refresh_color_2, R.color.swipe_refresh_color_3);
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
    public void initForecastList() {
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mWeatherRecyclerView.setLayoutManager(mLayoutManager);
        }
        mAdapter = new ForecastRecyclerViewAdapter();
        mWeatherRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateWeatherForecast(WeatherForecast weatherForecast) {
        if (mAdapter != null) {
            mAdapter.updateData(weatherForecast.getWeekForecast().getDayForecastList());
            mAdapter.notifyDataSetChanged();
        }

        CurrentWeather currentWeather = weatherForecast.getCurrentWeather();
        mTemperatureTextView.setText(String.valueOf(currentWeather.getTemp()));
        mSkyTextView.setText(currentWeather.getDescription());
        mLastWeatherUpdateTextView.setText(DateUtils.dateToString(weatherForecast.getLastUpdate()));
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
    public void showRefresh(boolean show) {
        if (show)
            getSupportActionBar().setTitle(R.string.refreshing);
        else
            getSupportActionBar().setTitle(R.string.weather);
        mSwipeRefresh.setRefreshing(show);
    }

    @Override
    public void setIcon(Bitmap icon) {
        setWeatherIcon(icon);
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

    @Override
    public void setWeatherIcon(Bitmap icon) {
        mWeatherIcon.setImageBitmap(icon);
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
