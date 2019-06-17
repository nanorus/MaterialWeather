package com.example.nanorus.materialweather.presentation.view.dayforecast;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.entity.weather.repository.HourForecast;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.TempUtils;
import com.example.nanorus.materialweather.presentation.presenter.dayforecast.DayForecastPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.DayForecastRecyclerViewAdapter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayForecastActivity extends AppCompatActivity {

    @Inject
    DayForecastPresenter presenter;
    @Inject
    ResourceManager resourceManager;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_temp_max)
    TextView tempMaxTextView;
    @BindView(R.id.tv_temp_min)
    TextView tempMinTextView;
    @BindView(R.id.tv_description)
    TextView descriptionTextView;
    @BindView(R.id.tv_city)
    TextView cityTextView;
    @BindView(R.id.iv_image)
    ImageView image;
    @BindView(R.id.appBar)
    Toolbar toolbar;
    @BindView(R.id.tv_last_update)
    TextView lastUpdateTextView;
    @BindView(R.id.rv_forecast)
    RecyclerView forecastRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DayForecastRecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_forecast);
        App.getApp().getDayForecastComponent().inject(this);
        ButterKnife.bind(this);

        initToolbar();
        swipeRefresh.setOnRefreshListener(() -> presenter.onSwipeRefresh());
        swipeRefresh.setColorSchemeResources(R.color.swipe_refresh_color_1, R.color.swipe_refresh_color_2, R.color.swipe_refresh_color_3);

        Date date = getDate();
        presenter.bindView(this);
        presenter.startPresenter(date);
    }

    public void initForecast() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            forecastRecyclerView.setLayoutManager(layoutManager);
        }
        adapter = new DayForecastRecyclerViewAdapter();
        forecastRecyclerView.setAdapter(adapter);
        forecastRecyclerView.addItemDecoration(new DividerItemDecoration(forecastRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    public void setInfo(DayForecast data) {
        tempMaxTextView.setText(TempUtils.temperatureToString(data.getTemperaturesAmplitude().getMaxTemperature()));
        tempMinTextView.setText(TempUtils.temperatureToString(data.getTemperaturesAmplitude().getMinTemperature()));
        descriptionTextView.setText(data.getHourForecastList().get(0).getDescription());
        cityTextView.setText(data.getPlace());
        Glide.with(this).load(resourceManager.getWeatherIconDrawable(data.getIcon())).into(image);
    }

    public void updateForecast(List<HourForecast> data) {
        if (adapter != null) {
            adapter.updateData(data);
            adapter.notifyDataSetChanged();
        }
    }

    public void setToolbarTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    private Date getDate() {
        Date date;
        long millis = getIntent().getLongExtra("date", -1);
        if (millis != -1)
            date = new Date(millis);
        else
            date = new Date();
        return date;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    public void setLastUpdate(String text) {
        lastUpdateTextView.setText(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            presenter.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public Activity getView() {
        return this;
    }

    public void showRefreshing(boolean b) {
        swipeRefresh.setRefreshing(b);
    }
}
