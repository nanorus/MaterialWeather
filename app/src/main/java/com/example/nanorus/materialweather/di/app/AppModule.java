package com.example.nanorus.materialweather.di.app;

import android.content.Context;

import com.example.nanorus.materialweather.model.data.ResourceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    ResourceManager provideResourceManager(Context context){
        return new ResourceManager(context);
    }

}
