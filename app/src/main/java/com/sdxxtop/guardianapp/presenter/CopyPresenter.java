package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CopyContract;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class CopyPresenter extends RxPresenter<CopyContract.IView> implements CopyContract.IPresenter {
    @Inject
    public CopyPresenter() {
    }


}
