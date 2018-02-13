package com.example.nanorus.materialweather.presentation.settings.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.presentation.settings.presenter.ISettingsPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    ISettingsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        App.getApp().getSettingsComponent().inject(this);

        mPresenter.bindView(this);
        mPresenter.startWork();

    }


    @Override
    public Activity getView() {
        return this;
    }
}
