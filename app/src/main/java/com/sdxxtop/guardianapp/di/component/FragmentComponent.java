package com.sdxxtop.guardianapp.di.component;

import android.app.Activity;

import com.sdxxtop.guardianapp.di.module.FragmentModule;
import com.sdxxtop.guardianapp.di.qualifier.FragmentScope;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.fragment.KQST_AttendanceFragment;
import com.sdxxtop.guardianapp.ui.fragment.CourseListFragment;
import com.sdxxtop.guardianapp.ui.fragment.DataMonitoringFragment;
import com.sdxxtop.guardianapp.ui.fragment.EventListFragment;
import com.sdxxtop.guardianapp.ui.fragment.GridEventFragment;
import com.sdxxtop.guardianapp.ui.fragment.HomeFragment;
import com.sdxxtop.guardianapp.ui.fragment.MineFragment;
import com.sdxxtop.guardianapp.ui.fragment.NewsListFragment;
import com.sdxxtop.guardianapp.ui.fragment.OrganizationListFragment;
import com.sdxxtop.guardianapp.ui.fragment.SectionEventFragment;
import com.sdxxtop.guardianapp.ui.fragment.WorkFragment;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class}, dependencies = AppComponent.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(HomeFragment homeFragment);
    void inject(DataMonitoringFragment fragment);

    void inject(NewsListFragment newsListFragment);
    void inject(CourseListFragment fragment);
    void inject(MineFragment fragment);
    void inject(SectionEventFragment fragment);
    void inject(WorkFragment fragment);
    void inject(GridEventFragment fragment);
    void inject(OrganizationListFragment fragment);
    void inject(EventListFragment fragment);
    void inject(KQST_AttendanceFragment fragment);
}
