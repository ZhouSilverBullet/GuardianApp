package com.sdxxtop.guardianapp.di.component;

import android.app.Activity;

import com.sdxxtop.guardianapp.di.module.ActivityModule;
import com.sdxxtop.guardianapp.di.qualifier.ActivityScope;
import com.sdxxtop.guardianapp.ui.activity.CenterMessage2Activity;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactActivity;
import com.sdxxtop.guardianapp.ui.activity.ContactSearchActivity;
import com.sdxxtop.guardianapp.ui.activity.DeviceCenterMsgActivity;
import com.sdxxtop.guardianapp.ui.activity.DeviceDataDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.DeviceListActivity;
import com.sdxxtop.guardianapp.ui.activity.DeviceWarnDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionListActivity;
import com.sdxxtop.guardianapp.ui.activity.EventDiscretionReportActivity;
import com.sdxxtop.guardianapp.ui.activity.EventMoveActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailSecondActivity;
import com.sdxxtop.guardianapp.ui.activity.EventStatistyActivity;
import com.sdxxtop.guardianapp.ui.activity.ExamineActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyDataListActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyEventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GACEventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.GACPatrolDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantCompanyReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantEventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GrantGridReportActivity;
import com.sdxxtop.guardianapp.ui.activity.GridEventActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.GridreportUserreportActivity;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.LoginActivity;
import com.sdxxtop.guardianapp.ui.activity.MonitorMapActivity;
import com.sdxxtop.guardianapp.ui.activity.NewsDetailsActivity;
import com.sdxxtop.guardianapp.ui.activity.PartEventListActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolPathActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolRecordActivity;
import com.sdxxtop.guardianapp.ui.activity.ReCheckActivity;
import com.sdxxtop.guardianapp.ui.activity.RecordLogActivity;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetail2Activity;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.SectionEventActivity;
import com.sdxxtop.guardianapp.ui.activity.SplashActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;
import com.sdxxtop.guardianapp.ui.activity.custom_event.CustomEventActivity;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.MyAssessActivity;
import com.sdxxtop.guardianapp.ui.activity.problemgj.ProblemGJDetailActivity;

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
//    void inject(EventReportListActivity activity);
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
    void inject(PartEventListActivity activity);
    void inject(GACPatrolDetailActivity activity);
    void inject(GrantGridReportActivity activity);
    void inject(GridreportUserreportActivity activity);
    void inject(EventDiscretionListActivity activity);
    void inject(EventDiscretionReportActivity activity);
    void inject(PatrolAddDetailActivity activity);
    void inject(ReCheckActivity activity);
    void inject(CenterMessageActivity activity);
    void inject(CenterMessage2Activity activity);
    void inject(MonitorMapActivity activity);
    void inject(DeviceDataDetailActivity activity);
    void inject(DeviceListActivity activity);
    void inject(DeviceCenterMsgActivity activity);
    void inject(DeviceWarnDetailActivity activity);
    void inject(SectionEventActivity activity);
    void inject(EventMoveActivity activity);
    void inject(FlyDataListActivity activity);
    void inject(FlyEventDetailActivity activity);
    void inject(FlyEventReportActivity activity);
    void inject(EventDetailActivity activity);
    void inject(GridEventActivity activity);
    void inject(EventReportDetailActivity_new activity);
    void inject(RecordLogActivity activity);
    void inject(CustomEventActivity activity);
    void inject(MyAssessActivity activity);
    void inject(ProblemGJDetailActivity activity);

}
