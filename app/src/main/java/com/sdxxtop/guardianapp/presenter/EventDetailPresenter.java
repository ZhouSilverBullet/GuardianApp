package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDetailContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class EventDetailPresenter extends RxPresenter<EventDetailContract.IView> implements EventDetailContract.IPresenter {
    @Inject
    public EventDetailPresenter() {
    }


}
