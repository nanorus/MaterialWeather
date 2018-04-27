package com.example.nanorus.materialweather.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.model.data.TempUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastRecyclerViewHolder> {

    private List<DayForecast> mData;

    public ForecastRecyclerViewAdapter() {
        mData = new ArrayList<>();
    }

    public void updateData(List<DayForecast> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        } else
            mData = data;
    }

    @Override
    public ForecastRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastRecyclerViewHolder holder, int position) {
        DayForecast dayForecast = mData.get(position);

        holder.dateTextView.setText(getDateString(dayForecast.getDate()));
        // set temperatures
        int minTemp = dayForecast.getTemperaturesAmplitude().getMinTemperature();
        int maxTemp = dayForecast.getTemperaturesAmplitude().getMaxTemperature();
        String tempMin = TempUtils.temperatureToString(minTemp);
        String tempMax = TempUtils.temperatureToString(maxTemp);
        holder.temperatureTextView.setText(tempMin + "°C" + " / " + tempMax + "°C");
    }

    private String getDateString(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        if (day.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
            return "Today, " + (new SimpleDateFormat("dd MMMM", Locale.ENGLISH)).format(date);
        } else {
            return (new SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH)).format(date);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else
            return 0;
    }

    public static class ForecastRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView temperatureTextView;

        public ForecastRecyclerViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.forecast_list_item_tv_date);
            temperatureTextView = itemView.findViewById(R.id.forecast_list_item_tv_temperature);
        }
    }


}
