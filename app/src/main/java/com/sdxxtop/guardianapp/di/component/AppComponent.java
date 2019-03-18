package com.sdxxtop.guardianapp.di.component;


import com.sdxxtop.guardianapp.app.App;
import com.sdxxtop.guardianapp.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    App getAppContext();
}
