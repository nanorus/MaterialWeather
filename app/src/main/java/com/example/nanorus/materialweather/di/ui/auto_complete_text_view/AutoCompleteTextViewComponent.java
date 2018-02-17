package com.example.nanorus.materialweather.di.ui.auto_complete_text_view;

import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.cities.CitiesAutoCompleteTextViewAdapter;

import dagger.Subcomponent;

@Subcomponent(modules = {AutoCompleteTextViewModule.class})
public interface AutoCompleteTextViewComponent {

    void inject(CitiesAutoCompleteTextViewAdapter adapter);

}
