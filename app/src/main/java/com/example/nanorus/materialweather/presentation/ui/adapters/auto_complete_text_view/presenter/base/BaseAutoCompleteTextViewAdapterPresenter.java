package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base;

import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.base.IAutoCompleteTextViewAdapter;

public abstract class BaseAutoCompleteTextViewAdapterPresenter implements IAutoCompleteTextViewAdapterPresenter {

    public IAutoCompleteTextViewAdapter mAdapter;

    public void bindView(IAutoCompleteTextViewAdapter adapter) {
        mAdapter = adapter;
    }

    public abstract void onPerformFiltering(CharSequence input);

}
