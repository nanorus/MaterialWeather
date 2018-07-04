package com.example.nanorus.materialweather.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.entity.weather.repository.HourForecast;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.TempUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class DayForecastRecyclerViewAdapter extends RecyclerView.Adapter<DayForecastRecyclerViewAdapter.DayForecastRecyclerViewHolder> {

    List<HourForecast> list;
    @Inject
    ResourceManager resourceManager;

    public DayForecastRecyclerViewAdapter() {
        this.list = new ArrayList<>();
        App.getApp().getAppComponent().inject(this);
    }

    public void updateData(List<HourForecast> data) {
        if (this.list != null) {
            this.list.clear();
            this.list.addAll(data);
        } else
            this.list = data;
    }


    @NonNull
    @Override
    public DayForecastRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_forecast_list_item, parent, false);
        return new DayForecastRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayForecastRecyclerViewHolder holder, int position) {
        HourForecast data = this.list.get(position);

        Calendar cal = Calendar.getInstance();
        cal.setTime(data.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        holder.timeTextView.setText(sdf.format(data.getDate()));
        holder.descriptionTextView.setText(data.getDescription());
        holder.tempTextView.setText(TempUtils.temperatureToString(data.getTemp()));
        Glide.with(holder.descriptionTextView.getContext())
                .load(resourceManager.getForecastIconDrawable(data.getIcon()))
                .into(holder.iconImageView);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public static class DayForecastRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView timeTextView;
        TextView descriptionTextView;
        TextView tempTextView;
        ImageView iconImageView;

        public DayForecastRecyclerViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.tv_time);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
            tempTextView = itemView.findViewById(R.id.tv_temp);
            iconImageView = itemView.findViewById(R.id.iv_icon);
        }
    }
}
