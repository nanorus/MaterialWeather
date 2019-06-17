package com.example.nanorus.materialweather.presentation.view.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity implements ISettingsActivity {
    private final String TAG = this.getClass().getSimpleName();

    private final int MENU_ITEM_SAVE = 1;

    @Inject
    ISettingsPresenter presenter;

    DelayAutoCompleteTextView cityEditText;
    ProgressBar progressBar;
    TextView cityTextView;
    TextView cityFoundedTextView;
    ImageView cityEnteredSuccessIconImageView;
    TextView successTextView;
    MenuItem saveMenuItem;
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
        //init();
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
/*        if (successTextView.getVisibility() == View.INVISIBLE) {
            successTextView.setVisibility(View.VISIBLE);
        }*/
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
            //  setCityFoundedTextProcess();
        } else {
            checkCityProgressBar.setVisibility(View.INVISIBLE);
            // hideCityFoundedText();
        }
    }

    @Override
    public void showSelectCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_city, null);
        cityEditText = view.findViewById(R.id.autoCompleteTextView_city);
        cityEnteredSuccessIconImageView = view.findViewById(R.id.imageView_city_entered_success_icon);
        progressBar = view.findViewById(R.id.progress_bar);
        checkCityProgressBar = view.findViewById(R.id.progress_bar_check_entered_city);
        cityTextView = view.findViewById(R.id.textView_city);
        cityFoundedTextView = view.findViewById(R.id.textView_city_founded);
        init();

        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onSaveClicked(cityEditText.getText().toString());
                setEnteredCity(cityEditText.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(view);
        builder.show();
    }

    @Override
    public void setCityFoundedTextSuccessful() {
        cityFoundedTextView.setTextColor(getResources().getColor(R.color.searchingCitySuccessful));
        cityFoundedTextView.setText(R.string.weather_city_found);
        cityFoundedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCityFoundedTextUnsuccessful() {
        cityFoundedTextView.setTextColor(getResources().getColor(R.color.searchingCityUnsuccessful));
        cityFoundedTextView.setText(R.string.weather_city_no_found);
        cityFoundedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCityFoundedTextProcess() {
        cityFoundedTextView.setTextColor(getResources().getColor(R.color.searchingCityProcess));
        cityFoundedTextView.setText(R.string.weather_city_process);
        cityFoundedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCityFoundedText() {
        cityFoundedTextView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void hideEnteredCitySuccessNotice() {
/*        if (successTextView.getVisibility() == View.VISIBLE) {
            successTextView.setVisibility(View.INVISIBLE);
        }
        cityEnteredSuccessIconImageView.setVisibility(View.INVISIBLE);*/
    }

    private void showSuccessNoticeLabel(boolean success) {
/*        if (success) {
            successTextView.setText(R.string.weather_city_found);
            successTextView.setTextColor(getResources().getColor(R.color.white));
        } else {
            successTextView.setText(R.string.weather_city_no_found);
            successTextView.setTextColor(getResources().getColor(R.color.red));
        }*/
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

    @OnClick({R.id.textView_locality_label, R.id.textView_city})
    void onLocalityClick() {
        showSelectCityDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.releasePresenter();
        presenter = null;
    }


}
