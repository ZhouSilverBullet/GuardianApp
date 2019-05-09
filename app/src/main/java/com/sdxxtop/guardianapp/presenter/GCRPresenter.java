package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GCRContract;

import javax.inject.Inject;

/**
 * GrantCompanyReportPresenter
 */
public class GCRPresenter extends RxPresenter<GCRContract.IView> implements GCRContract.IPresenter {
    @Inject
    public GCRPresenter() {
    }


}
