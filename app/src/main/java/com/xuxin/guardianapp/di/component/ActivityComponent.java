package com.xuxin.guardianapp.di.component;

import android.app.Activity;

import com.xuxin.guardianapp.di.module.ActivityModule;
import com.xuxin.guardianapp.di.qualifier.ActivityScope;
import com.xuxin.guardianapp.ui.activity.HomeActivity;
import com.xuxin.guardianapp.ui.activity.LoginActivity;
import com.xuxin.guardianapp.ui.activity.NewsDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);
    void inject(NewsDetailsActivity newsDetailsActivity);
}
