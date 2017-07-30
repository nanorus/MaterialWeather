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
        String strDt = null;
        if (position == 0) {
            if (singleData.getDayOfMonth() == Integer.parseInt(new SimpleDateFormat("dd").format(Calendar.getInstance().getTime()))) {
                strDt = "Today";
            }
        } else {
            Date date = new Date();
            date.setDate(singleData.getDayOfMonth());
            date.setMonth(singleData.getMonth());
            strDt = (new SimpleDateFormat("EEEE, dd MMMM")).format(date);
        }
        holder.forecast_list_item_tv_date.setText(strDt);
        holder.forecast_list_item_tv_temperature.setText(singleData.getMinTemp() + " - " + singleData.getMaxTemp() + "°");

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
