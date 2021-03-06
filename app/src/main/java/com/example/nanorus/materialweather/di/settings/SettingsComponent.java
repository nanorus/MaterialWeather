package com.example.nanorus.materialweather.di.settings;

import com.example.nanorus.materialweather.presentation.view.settings.SettingsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {SettingsModule.class})
public interface SettingsComponent {

    void inject(SettingsActivity activity);

}
