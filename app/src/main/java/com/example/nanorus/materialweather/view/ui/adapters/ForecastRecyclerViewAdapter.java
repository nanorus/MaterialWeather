package com.example.nanorus.materialweather.view.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.model.pojo.forecast.ListPojo;

import java.util.List;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastRecyclerViewHolder> {

    List<ListPojo> mData;

    public ForecastRecyclerViewAdapter(List<ListPojo> data) {
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
        holder.forecast_list_item_tv_date.setText(mData.get(position).getDtTxt());
        holder.forecast_list_item_tv_temperature.setText(String.valueOf(Math.round(mData.get(position).getMain().getTemp()) - 273) + "°");

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
