package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.cities;

import android.util.Log;

import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.data.api.services.IpLocationService;
import com.example.nanorus.materialweather.data.api.services.SearchPossibleCitiesService;
import com.example.nanorus.materialweather.data.entity.search_possible_cities.Prediction;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base.BaseAutoCompleteTextViewAdapterPresenter;

import javax.inject.Inject;

import rx.Completable;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CitiesAutoCompleteTextViewAdapterPresenter extends BaseAutoCompleteTextViewAdapterPresenter {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    SearchPossibleCitiesService searchPossibleCitiesService;
    @Inject
    IpLocationService ipLocationService;

    public CitiesAutoCompleteTextViewAdapterPresenter() {
        App.getApp().getAppComponent().inject(this);
    }

    @Override
    public void onPerformFiltering(CharSequence input) {
        String searcePlace = input.toString();
        mAdapter.clearResults();
        if (!searcePlace.isEmpty()) {

            Observable<String> citiesObservable = ipLocationService.getIpLocation()
                    .toObservable()
                    .flatMap(ipLocation -> searchPossibleCitiesService.getPossibleCities(searcePlace, ipLocation.getLat() + "," + ipLocation.getLon()))
                    .flatMap(predictionList -> Observable.from(predictionList.getPredictions()))
                    .map(Prediction::getDescription);
            Completable startProgressBarCompletable = Completable.create(completableSubscriber -> mAdapter.startProgressBar());
            startProgressBarCompletable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread()).subscribe();

            citiesObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "get cities onCompleted");
                            mAdapter.stopProgressBar();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "get cities onError: " + e.getMessage());
                            mAdapter.stopProgressBar();
                            mAdapter.notifyDataSetInvalidated();
                        }

                        @Override
                        public void onNext(String s) {
                            mAdapter.addResult(s);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
}
