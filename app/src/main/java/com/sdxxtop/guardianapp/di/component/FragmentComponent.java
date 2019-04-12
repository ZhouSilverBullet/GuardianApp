package com.sdxxtop.guardianapp.di.component;

import android.app.Activity;

import com.sdxxtop.guardianapp.di.module.FragmentModule;
import com.sdxxtop.guardianapp.di.qualifier.FragmentScope;
import com.sdxxtop.guardianapp.ui.fragment.CourseListFragment;
import com.sdxxtop.guardianapp.ui.fragment.HomeFragment;
import com.sdxxtop.guardianapp.ui.fragment.MineFragment;
import com.sdxxtop.guardianapp.ui.fragment.NewsListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(NewsListFragment newsListFragment);
    void inject(CourseListFragment fragment);
    void inject(MineFragment fragment);
}
