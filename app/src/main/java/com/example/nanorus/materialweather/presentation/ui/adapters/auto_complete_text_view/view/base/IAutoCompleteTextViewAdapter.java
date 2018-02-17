package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.base;

import java.util.List;

public interface IAutoCompleteTextViewAdapter {
    void clearResults();

    void updateResults(List<String> results);

    void addResult(String result);

    void notifyDataSetChanged();

    void notifyDataSetInvalidated();

}
