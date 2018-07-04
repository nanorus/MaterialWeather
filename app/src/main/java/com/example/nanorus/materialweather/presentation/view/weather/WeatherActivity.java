package com.example.nanorus.materialweather.presentation.view.weather;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    IWeatherPresenter presenter;
    @Inject
    ResourceManager resourceManager;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    ConstraintLayout navigationHeader;
    ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.weather_tv_place)
    TextView placeTextView;
    @BindView(R.id.weather_rv_weatherList)
    RecyclerView weatherRecyclerView;
    @BindView(R.id.weather_now_tv_sky)
    TextView skyTextView;
    @BindView(R.id.weather_now_tv_temperature)
    TextView temperatureTextView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.textView_last_update_value)
    TextView lastWeatherUpdateTextView;
    @BindView(R.id.imageView_weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.appBar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private ForecastRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.weather));

        setupNavigationDrawer();
        initUi();

        App.getApp().getWeatherComponent().inject(this);
        presenter.bindView(getView());
        presenter.startPresenter();
        swipeRefresh.setOnRefreshListener(() -> presenter.onRefresh());
        swipeRefresh.setColorSchemeResources(R.color.swipe_refresh_color_1, R.color.swipe_refresh_color_2, R.color.swipe_refresh_color_3);
    }

    @Override
    protected void onDestroy() {
        presenter.releasePresenter();
        presenter = null;
        App.getApp().releaseWeatherComponent();
        super.onDestroy();
    }


    @Override
    public void initForecastList() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            weatherRecyclerView.setLayoutManager(layoutManager);
        }
        adapter = new ForecastRecyclerViewAdapter();
        weatherRecyclerView.setAdapter(adapter);
    }

    @Override
    public void updateWeatherForecast(WeatherForecast weatherForecast) {
        if (adapter != null) {
            adapter.updateData(weatherForecast.getWeekForecast().getDayForecastList());
            adapter.notifyDataSetChanged();
        }

        CurrentWeather currentWeather = weatherForecast.getCurrentWeather();
        temperatureTextView.setText(String.valueOf(currentWeather.getTemp()));
        skyTextView.setText(currentWeather.getDescription());
        lastWeatherUpdateTextView.setText(DateUtils.dateToString(weatherForecast.getLastUpdate()));
        placeTextView.setText(currentWeather.getPlace());
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
        swipeRefresh.setRefreshing(show);
    }

    @Override
    public void setIcon(Bitmap icon) {
        setWeatherIcon(icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout != null)
            drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setWeatherIcon(Bitmap icon) {
        weatherIcon.setImageBitmap(icon);
    }

    private void setupNavigationDrawer() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationHeader = (ConstraintLayout) navigationView.getHeaderView(0);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
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
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_item_settings:
                    presenter.onSettingsClick();
                    break;
            }
            return false;
        });
        navigationHeader.findViewById(R.id.button_menu_changeCity).setOnClickListener(view -> presenter.onSettingsClick());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean result = true;
        if (resultCode == RESULT_OK)
            result = true;
        else if (resultCode == RESULT_CANCELED)
            result = false;

        presenter.onActivityResult(result, placeTextView.getText().toString());
    }

    private void initUi() {
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }


}
