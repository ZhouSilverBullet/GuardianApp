package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

public class EventReportPresenter extends RxPresenter<EventReportContract.IView> implements EventReportContract.IPresenter {
    @Inject
    public EventReportPresenter() {
    }


    public void pushReport(List<File> imagePushPath) {

    }

}
