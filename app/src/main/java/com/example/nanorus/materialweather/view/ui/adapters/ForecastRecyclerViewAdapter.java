package com.example.nanorus.materialweather.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.model.pojo.ShortDayWeatherPojo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastRecyclerViewHolder> {

    List<ShortDayWeatherPojo> mData;

    public ForecastRecyclerViewAdapter(List<ShortDayWeatherPojo> data) {
        mData = data;
    }

    @Override
    public ForecastRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);

        ForecastRecyclerViewHolder holder = new ForecastRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ForecastRecyclerViewHolder holder, int position) {
        ShortDayWeatherPojo singleData = mData.get(position);

        // set date
        String strDt = null;
        Date date = new Date();
        date.setDate(singleData.getDayOfMonth());
        date.setMonth(singleData.getMonth());
        if (position == 0) {
            if (singleData.getDayOfMonth() == Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()))) {
                strDt = "Today, " + (new SimpleDateFormat("dd MMMM", Locale.ENGLISH)).format(date);
            }
        } else {
            strDt = (new SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH)).format(date);
        }
        holder.forecast_list_item_tv_date.setText(strDt);

        // set temperatures
        String tempMin = null;
        String tempMax = null;

        if (singleData.getMinTemp() > 0)
            tempMin = "+" + String.valueOf(singleData.getMinTemp());
        else if (singleData.getMinTemp() < 0)
            tempMin = String.valueOf(singleData.getMinTemp());

        if (singleData.getMaxTemp() > 0)
            tempMax = "+" + String.valueOf(singleData.getMaxTemp());
        else if (singleData.getMaxTemp() < 0)
            tempMax = String.valueOf(singleData.getMaxTemp());

        holder.forecast_list_item_tv_temperature.setText(tempMin + "°C" + " / " + tempMax + "°C");

    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else
            return 0;
    }

    public static class ForecastRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView forecast_list_item_tv_date;
        TextView forecast_list_item_tv_temperature;

        public ForecastRecyclerViewHolder(View itemView) {
            super(itemView);
            forecast_list_item_tv_date = (TextView) itemView.findViewById(R.id.forecast_list_item_tv_date);
            forecast_list_item_tv_temperature = (TextView) itemView.findViewById(R.id.forecast_list_item_tv_temperature);
        }
    }


}
