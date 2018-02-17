package com.example.nanorus.materialweather.presentation.settings.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.presentation.custom.DelayAutoCompleteTextView;
import com.example.nanorus.materialweather.presentation.settings.presenter.ISettingsPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.cities.CitiesAutoCompleteTextViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    ISettingsPresenter mPresenter;

    @BindView(R.id.autoCompleteTextView_city)
    DelayAutoCompleteTextView mCityAutoCompleteTextView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    CitiesAutoCompleteTextViewAdapter mCityAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.settings);

        App.getApp().getSettingsComponent().inject(this);

        mPresenter.bindView(this);
        mPresenter.startWork();


        mCityAutoCompleteAdapter = new CitiesAutoCompleteTextViewAdapter(getView());
        mCityAutoCompleteTextView.setThreshold(1);
        mCityAutoCompleteTextView.setLoadingIndicator(mProgressBar);
        mCityAutoCompleteTextView.setAdapter(mCityAutoCompleteAdapter);
    }


    @Override
    public Activity getView() {
        return this;
    }
}
