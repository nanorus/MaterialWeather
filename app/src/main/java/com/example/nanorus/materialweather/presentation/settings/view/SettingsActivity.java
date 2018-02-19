package com.example.nanorus.materialweather.presentation.settings.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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

    private final int MENU_ITEM_SAVE = 1;

    @Inject
    ISettingsPresenter mPresenter;

    @BindView(R.id.autoCompleteTextView_city)
    DelayAutoCompleteTextView mCityEditText;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.textView_city)
    TextView mCityTextView;
    @BindView(R.id.imageView_city_entered_success_icon)
    ImageView cityEnteredSuccessIconImageView;

    CitiesAutoCompleteTextViewAdapter mCityAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        App.getApp().getSettingsComponent().inject(this);

        mPresenter.bindView(this);
        mPresenter.startWork();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_ITEM_SAVE, Menu.NONE, "save");
        menu.findItem(MENU_ITEM_SAVE)
                .setIcon(R.drawable.ic_check_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_SAVE:
                mPresenter.onSaveClicked(mCityTextView.getText().toString());
                break;
            case android.R.id.home:
                mPresenter.onHomeClicked();
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public Activity getView() {
        return this;
    }

    @Override
    public void setCity(String city) {
        mCityTextView.setText(city);
    }

    @Override
    public void setEnteredCity(String enteringCity) {
        mCityEditText.setText(enteringCity);
    }

    @Override
    public void setEnteredCitySuccess(boolean success) {
        int iconResId = R.drawable.ic_close_red_24dp;
        if (success)
            iconResId = R.drawable.ic_check_white_24dp;
        cityEnteredSuccessIconImageView.setImageResource(iconResId);
    }

    private void init() {
        mCityAutoCompleteAdapter = new CitiesAutoCompleteTextViewAdapter(getView(), mProgressBar);
        mCityEditText.setAdapter(mCityAutoCompleteAdapter);
        mCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.onCitiesAutoCompleteTextChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCityEditText.setOnItemClickListener((adapterView, view, i, l) ->
                mPresenter.onCitiesAutoCompleteItemClicked((String) adapterView.getItemAtPosition(i)));
    }

    @Override
    public void playEnteredTextFailAnimation() {
        YoYo.with(Techniques.Shake)
                .duration(900)
                .delay(100)
                .playOn(mCityEditText);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.releasePresenter();
        mPresenter = null;
    }
}
