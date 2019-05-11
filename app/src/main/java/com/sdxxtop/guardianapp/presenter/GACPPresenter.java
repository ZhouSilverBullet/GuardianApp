package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GACPContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class GACPPresenter extends RxPresenter<GACPContract.IView> implements GACPContract.IPresenter {
    @Inject
    public GACPPresenter() {
    }


}
