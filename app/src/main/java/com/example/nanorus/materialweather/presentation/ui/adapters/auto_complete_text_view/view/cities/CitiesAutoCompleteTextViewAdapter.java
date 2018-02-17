package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.cities;

import android.content.Context;

import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base.IAutoCompleteTextViewAdapterPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.base.BaseAutoCompleteTextViewAdapter;

import javax.inject.Inject;
import javax.inject.Named;


public class CitiesAutoCompleteTextViewAdapter extends BaseAutoCompleteTextViewAdapter {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    @Named("CitiesAutoCompleteTextViewAdapterPresenter")
    IAutoCompleteTextViewAdapterPresenter mPresenter;

    public CitiesAutoCompleteTextViewAdapter(Context context) {
        super(context);
        App.getApp().getAutoCompleteTextViewComponent().inject(this);
        setPresenter(mPresenter);
        mPresenter.bindView(this);
    }
}
