package com.example.nanorus.materialweather.di.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }


    @Provides
    Context provideContext() {
        return mContext;
    }

}
