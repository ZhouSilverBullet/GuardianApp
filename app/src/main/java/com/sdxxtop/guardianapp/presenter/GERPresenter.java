package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GERContract;

import javax.inject.Inject;

/**
 * GERPresenter(GrandEventReportPresenter)
 */
public class GERPresenter extends RxPresenter<GERContract.IView> implements GERContract.IPresenter {
    @Inject
    public GERPresenter() {
    }



}
