package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class PatrolPathPresenter extends RxPresenter<PatrolContract.IView> implements PatrolContract.IPresenter {
    @Inject
    public PatrolPathPresenter() {
    }


}
