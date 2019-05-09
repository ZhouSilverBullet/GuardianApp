package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventStatistyContract;

import javax.inject.Inject;

/**
 * 事件统计
 */
public class EventStatistyPresenter extends RxPresenter<EventStatistyContract.IView> implements EventStatistyContract.IPresenter {
    @Inject
    public EventStatistyPresenter() {
    }


}
