package com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.presenter.base.IAutoCompleteTextViewAdapterPresenter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAutoCompleteTextViewAdapter extends BaseAdapter implements Filterable, IAutoCompleteTextViewAdapter {

    private Context mContext;
    private List<String> mResults;
    private IAutoCompleteTextViewAdapterPresenter mPresenter;
    private ProgressBar mProgressBar;

    public BaseAutoCompleteTextViewAdapter(Context context, ProgressBar progressBar) {
        mContext = context;
        mResults = new ArrayList<>();
        mProgressBar = progressBar;
    }

    public void setPresenter(IAutoCompleteTextViewAdapterPresenter presenter) {
        mPresenter = presenter;
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
        ((TextView) view.findViewById(android.R.id.text1)).setText(mResults.get(i));
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                mPresenter.onPerformFiltering(charSequence);
                return new FilterResults();
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            }
        };
    }


    public void clearResults() {
        mResults.clear();
    }

    public void updateResults(List<String> results) {
        mResults.clear();
        mResults.addAll(results);
    }

    @Override
    public void addResult(String result) {
        mResults.add(result);
    }

    @Override
    public void startProgressBar() {
        if (!mProgressBar.isEnabled() || mProgressBar.getVisibility() != View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setEnabled(true);
        }
    }

    @Override
    public void stopProgressBar() {
        if (mProgressBar.isEnabled() || mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setEnabled(false);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
