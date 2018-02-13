package com.example.nanorus.materialweather.di.settings;

import com.example.nanorus.materialweather.presentation.settings.view.SettingsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {SettingsModule.class})
public interface SettingsComponent {

    void inject(SettingsActivity activity);

}
