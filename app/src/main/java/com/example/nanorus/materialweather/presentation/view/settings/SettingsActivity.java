package com.example.nanorus.materialweather.presentation.view.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.nanorus.materialweather.R;
import com.example.nanorus.materialweather.app.App;
import com.example.nanorus.materialweather.presentation.ui.custom.DelayAutoCompleteTextView;
import com.example.nanorus.materialweather.presentation.presenter.settings.ISettingsPresenter;
import com.example.nanorus.materialweather.presentation.ui.adapters.auto_complete_text_view.view.cities.CitiesAutoCompleteTextViewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsActivity {
    private final String TAG = this.getClass().getSimpleName();

    private final int MENU_ITEM_SAVE = 1;

    @Inject
    ISettingsPresenter presenter;

    @BindView(R.id.autoCompleteTextView_city)
    DelayAutoCompleteTextView cityEditText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.textView_city)
    TextView cityTextView;
    @BindView(R.id.imageView_city_entered_success_icon)
    ImageView cityEnteredSuccessIconImageView;
    @BindView(R.id.successTextView)
    TextView successTextView;
    MenuItem saveMenuItem;
    @BindView(R.id.progress_bar_check_entered_city)
    ProgressBar checkCityProgressBar;

    CitiesAutoCompleteTextViewAdapter cityAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        App.getApp().getSettingsComponent().inject(this);

        presenter.bindView(this);
        presenter.startWork();
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MENU_ITEM_SAVE, Menu.NONE, "save");
        saveMenuItem = menu.findItem(MENU_ITEM_SAVE);
        saveMenuItem
                .setIcon(R.drawable.ic_check_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        saveMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_SAVE:
                presenter.onSaveClicked(cityTextView.getText().toString());
                break;
            case android.R.id.home:
                presenter.onHomeClicked();
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
        cityTextView.setText(city);
    }

    @Override
    public void setEnteredCity(String enteringCity) {
        cityEditText.setText(enteringCity);
    }

    @Override
    public void showEnteredCitySuccessNotice(boolean success) {
        cityEnteredSuccessIconImageView.setVisibility(View.VISIBLE);
        if (successTextView.getVisibility() == View.INVISIBLE) {
            successTextView.setVisibility(View.VISIBLE);
        }
        int iconResId = R.drawable.ic_check_white_24dp;
        if (!success) {
            iconResId = R.drawable.ic_close_red_24dp;
            playEnteredTextFailAnimation();
        }
        cityEnteredSuccessIconImageView.setImageResource(iconResId);
        showSuccessNoticeLabel(success);
    }

    @Override
    public void showSaveButton(boolean show) {
        if (show) {
            saveMenuItem.setVisible(true);
        } else {
            saveMenuItem.setVisible(false);
        }
    }

    @Override
    public void showCheckCityProgress(boolean show) {
        if (show) {
            checkCityProgressBar.setVisibility(View.VISIBLE);
        } else {
            checkCityProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void hideEnteredCitySuccessHotice() {
        if (successTextView.getVisibility() == View.VISIBLE) {
            successTextView.setVisibility(View.INVISIBLE);
        }
        cityEnteredSuccessIconImageView.setVisibility(View.INVISIBLE);
    }

    private void showSuccessNoticeLabel(boolean success) {
        if (success) {
            successTextView.setText(R.string.city_found);
            successTextView.setTextColor(getResources().getColor(R.color.white));
        } else {
            successTextView.setText(R.string.city_no_found);
            successTextView.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private void init() {
        cityAutoCompleteAdapter = new CitiesAutoCompleteTextViewAdapter(getView(), progressBar);
        cityEditText.setAdapter(cityAutoCompleteAdapter);
        cityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onCitiesAutoCompleteTextChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cityEditText.setOnItemClickListener((adapterView, view, i, l) ->
                presenter.onCitiesAutoCompleteItemClicked((String) adapterView.getItemAtPosition(i)));
    }

    public void playEnteredTextFailAnimation() {
        YoYo.with(Techniques.Shake)
                .duration(900)
                .delay(100)
                .playOn(cityEditText);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.releasePresenter();
        presenter = null;
    }


}
