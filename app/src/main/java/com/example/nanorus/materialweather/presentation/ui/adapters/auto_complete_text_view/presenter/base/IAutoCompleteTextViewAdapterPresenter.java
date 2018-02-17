package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base;

import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.base.IAutoCompleteTextViewAdapter;

public interface IAutoCompleteTextViewAdapterPresenter {

    void bindView(IAutoCompleteTextViewAdapter adapter);

    void onPerformFiltering(CharSequence input);

}
