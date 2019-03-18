package com.sdxxtop.guardianapp.di.module;



import com.sdxxtop.guardianapp.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App getApp() {
        return app;
    }
}
