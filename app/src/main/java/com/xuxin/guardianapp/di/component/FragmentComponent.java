package com.xuxin.guardianapp.di.component;

import android.app.Activity;


import com.xuxin.guardianapp.di.module.FragmentModule;
import com.xuxin.guardianapp.di.qualifier.FragmentScope;
import com.xuxin.guardianapp.ui.fragment.HomeFragment;
import com.xuxin.guardianapp.ui.fragment.NewsListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(NewsListFragment newsListFragment);
}
