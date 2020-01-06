package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CopyContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class MyAssessPresenter extends RxPresenter<CopyContract.IView> implements CopyContract.IPresenter {
    @Inject
    public MyAssessPresenter() {
    }


}
