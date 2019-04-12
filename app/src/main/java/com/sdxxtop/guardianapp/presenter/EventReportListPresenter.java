package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportListContract;

import javax.inject.Inject;

/**
 * 发现接口一样
 * 暂时使用
 * TaskAgentsPresenter
 * 代替
 */
public class EventReportListPresenter extends RxPresenter<EventReportListContract.IView> implements EventReportListContract.IPresenter {
    @Inject
    public EventReportListPresenter() {
    }


}
