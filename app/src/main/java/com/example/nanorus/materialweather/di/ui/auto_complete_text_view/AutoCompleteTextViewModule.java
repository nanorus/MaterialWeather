package com.example.nanorus.materialweather.di.ui.auto_complete_text_view;

import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base.IAutoCompleteTextViewAdapterPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.cities.CitiesAutoCompleteTextViewAdapterPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AutoCompleteTextViewModule {

    @Provides
    @Named("CitiesAutoCompleteTextViewAdapterPresenter")
    IAutoCompleteTextViewAdapterPresenter bindCitiesAutoCompleteTextViewAdapterPresenter(){
        return new CitiesAutoCompleteTextViewAdapterPresenter();
    }
}
