package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyDataListContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class FlyDataListPresenter extends RxPresenter<FlyDataListContract.IView> implements FlyDataListContract.IPresenter {
    @Inject
    public FlyDataListPresenter() {
    }


}
