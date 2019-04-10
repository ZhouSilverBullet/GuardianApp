package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
import com.sdxxtop.guardianapp.presenter.contract.SplashContract;

import javax.inject.Inject;

public class SplashPresenter extends RxPresenter<SplashContract.IView> implements SplashContract.IPresenter {
    @Inject
    public SplashPresenter() {
    }


}
