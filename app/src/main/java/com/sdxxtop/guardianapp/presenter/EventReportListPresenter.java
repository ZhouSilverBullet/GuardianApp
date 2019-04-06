package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportListContract;

import javax.inject.Inject;

public class EventReportListPresenter extends RxPresenter<EventReportListContract.IView> implements EventReportListContract.IPresenter {
    @Inject
    public EventReportListPresenter() {
    }


}
