package com.example.nanorus.materialweather.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.entity.weather.repository.DayForecast;
import com.example.nanorus.materialweather.model.data.DateUtils;
import com.example.nanorus.materialweather.model.data.ResourceManager;
import com.example.nanorus.materialweather.model.data.TempUtils;
import com.example.nanorus.materialweather.navigation.Router;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastRecyclerViewHolder> {

    private List<DayForecast> list;
    @Inject
    ResourceManager resourceManager;
    @Inject
    Router router;

    public ForecastRecyclerViewAdapter() {
        list = new ArrayList<>();
        App.getApp().getAppComponent().inject(this);
    }

    public void updateData(List<DayForecast> data) {
        if (this.list != null) {
            this.list.clear();
            this.list.addAll(data);
        } else
            this.list = data;
    }

    @Override
    public ForecastRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastRecyclerViewHolder holder, int position) {
        DayForecast dayForecast = list.get(position);

        holder.dateTextView.setText(DateUtils.getDayString(dayForecast.getDate()));
        holder.descriptionTextView.setText(dayForecast.getHourForecastList().get(0).getDescription());
        // set temperatures
        int minTemp = dayForecast.getTemperaturesAmplitude().getMinTemperature();
        int maxTemp = dayForecast.getTemperaturesAmplitude().getMaxTemperature();
        String tempMin = TempUtils.temperatureToString(minTemp);
        String tempMax = TempUtils.temperatureToString(maxTemp);
        holder.tempMaxTextView.setText(tempMax);
        holder.tempMinTextView.setText(tempMin);
        Glide.with(holder.iconImageView.getContext())
                .load(resourceManager.getForecastIconDrawable(dayForecast.getIcon()))
                .into(holder.iconImageView);

        holder.itemView.setOnClickListener(view -> router.startDayForecastActivity(holder.itemView.getContext(), dayForecast.getDate()));

    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    public static class ForecastRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView tempMaxTextView;
        TextView tempMinTextView;
        ImageView iconImageView;
        TextView descriptionTextView;

        public ForecastRecyclerViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.forecast_list_item_tv_date);
            tempMaxTextView = itemView.findViewById(R.id.tv_temp_max);
            tempMinTextView = itemView.findViewById(R.id.tv_temp_min);
            iconImageView = itemView.findViewById(R.id.imageView_icon);
            descriptionTextView = itemView.findViewById(R.id.tv_description);
        }
    }


}
