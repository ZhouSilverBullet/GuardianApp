package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GACEContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class GACEPresenter extends RxPresenter<GACEContract.IView> implements GACEContract.IPresenter {
    @Inject
    public GACEPresenter() {
    }


}
