package com.example.nanorus.materialweather.di.app;

import com.example.nanorus.materialweather.navigation.Router;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RouterModule {

    @Provides
    @Singleton
    Router provideRouter() {
        return new Router();
    }
}
