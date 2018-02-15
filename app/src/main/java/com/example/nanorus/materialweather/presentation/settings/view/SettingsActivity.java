package com.example.nanorus.materialweather.presentation.settings.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.presentation.custom.DelayAutoCompleteTextView;
import com.example.nanorus.materialweather.presentation.settings.presenter.ISettingsPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.CitiesAutoCompleteAdapter;

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

    CitiesAutoCompleteAdapter mPossibleCitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.settings);

        App.getApp().getSettingsComponent().inject(this);

        mPresenter.bindView(this);
        mPresenter.startWork();


        mPossibleCitiesAdapter = new CitiesAutoCompleteAdapter(getView());
        mCityAutoCompleteTextView.setThreshold(1);
        mCityAutoCompleteTextView.setAdapter(mPossibleCitiesAdapter);
        mCityAutoCompleteTextView.setLoadingIndicator(mProgressBar);

    }


    @Override
    public Activity getView() {
        return this;
    }
}
