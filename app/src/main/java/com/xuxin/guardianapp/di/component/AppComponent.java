package com.xuxin.guardianapp.di.component;


import com.xuxin.guardianapp.app.App;
import com.xuxin.guardianapp.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    App getAppContext();
}
