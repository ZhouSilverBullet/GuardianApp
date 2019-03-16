package com.xuxin.guardianapp.di.module;

import android.app.Activity;


import com.xuxin.guardianapp.di.qualifier.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity getActivity() {
        return activity;
    }
}
