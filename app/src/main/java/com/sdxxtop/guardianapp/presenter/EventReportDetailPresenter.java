package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;

import javax.inject.Inject;

public class EventReportDetailPresenter extends RxPresenter<EventReportDetailContract.IView> implements EventReportDetailContract.IPresenter {
    @Inject
    public EventReportDetailPresenter() {
    }


}
