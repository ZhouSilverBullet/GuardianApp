package com.sdxxtop.guardianapp.di.component;

import android.app.Activity;

import com.sdxxtop.guardianapp.di.module.ActivityModule;
import com.sdxxtop.guardianapp.di.qualifier.ActivityScope;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.activity.NewsDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);
    void inject(NewsDetailsActivity newsDetailsActivity);
}
