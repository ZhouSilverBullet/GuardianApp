package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventDetailContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class FlyEventDetailPresenter extends RxPresenter<FlyEventDetailContract.IView> implements FlyEventDetailContract.IPresenter {
    @Inject
    public FlyEventDetailPresenter() {
    }


}
