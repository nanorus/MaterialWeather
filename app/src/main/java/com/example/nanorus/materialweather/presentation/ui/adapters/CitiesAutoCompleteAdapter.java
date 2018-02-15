package com.example.nanorus.materialweather.presentation.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.data.api.services.IpLocationService;
import com.example.nanorus.materialweather.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.data.entity.search_possible_cities.Prediction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CitiesAutoCompleteAdapter extends BaseAdapter implements Filterable {
    private final String TAG = this.getClass().getSimpleName();

    private final Context mContext;
    private List<String> mResults;

    @Inject
    SearchPossibleCitiesService searchPossibleCitiesService;
    @Inject
    IpLocationService ipLocationService;

    public CitiesAutoCompleteAdapter(Context context) {
        mContext = context;
        mResults = new ArrayList<>();
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int i) {
        return mResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
        }
        String city = mResults.get(i);
        ((TextView) view.findViewById(android.R.id.text1)).setText(city);
        ipLocationService.getIpLocation().toObservable();

        Single.fromCallable(() -> {
            return new Object(); // query
        }).toObservable()
                .flatMap(o -> Observable.fromCallable(() -> "test"));
        return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                mResults.clear();
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    String input = charSequence.toString();

                    filterResults.values = ipLocationService.getIpLocation()
                            .toObservable()
                            .flatMap(ipLocation -> searchPossibleCitiesService.getPossibleCities(input, ipLocation.getLat() + "," + ipLocation.getLon()))
                            .flatMap(predictionList -> Observable.from(predictionList.getPredictions()))
                            .map(Prediction::getDescription);
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.values != null) {
                    Observable<String> citiesObservable = (Observable<String>) filterResults.values;
                    citiesObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(TAG, "get cities onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "get cities onError: " + e.getMessage());
                                    notifyDataSetInvalidated();
                                }

                                @Override
                                public void onNext(String s) {
                                    mResults.add(s);
                                    notifyDataSetChanged();
                                }
                            });
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

}
