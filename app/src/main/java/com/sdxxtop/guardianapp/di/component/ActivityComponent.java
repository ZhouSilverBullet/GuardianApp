package com.sdxxtop.guardianapp.di.component;

import android.app.Activity;

import com.sdxxtop.guardianapp.di.module.ActivityModule;
import com.sdxxtop.guardianapp.di.qualifier.ActivityScope;
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactSearchActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailSecondActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportListActivity;
import com.sdxxtop.guardianapp.ui.activity.EventStatistyActivity;
import com.sdxxtop.guardianapp.ui.activity.ExamineActivity;
import com.sdxxtop.guardianapp.ui.activity.GACEventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantCompanyReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.activity.NewsDetailsActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolPathActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetail2Activity;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.SplashActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);
    void inject(NewsDetailsActivity newsDetailsActivity);
    void inject(ContactActivity contactActivity);
    void inject(EventReportActivity eventReportActivity);
    void inject(PatrolRecordActivity patrolRecordActivity);
    void inject(EventReportListActivity activity);
    void inject(EventReportDetailActivity activity);
    void inject(ExamineActivity activity);
    void inject(EventReportDetailSecondActivity activity);
    void inject(GridMapActivity activity);
    void inject(SplashActivity activity);
    void inject(TaskAgentsActivity activity);
    void inject(ContactSearchActivity activity);
    void inject(GrantEventReportActivity activity);
    void inject(EventStatistyActivity activity);
    void inject(GrantCompanyReportActivity activity);
    void inject(PatrolPathActivity activity);
    void inject(GACEventDetailActivity activity);
    void inject(SafeStaffDetailActivity activity);
    void inject(SafeStaffDetail2Activity activity);

}
