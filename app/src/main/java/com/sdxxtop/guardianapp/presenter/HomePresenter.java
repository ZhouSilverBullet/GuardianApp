package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
import com.sdxxtop.guardianapp.base.RxPresenter;

import javax.inject.Inject;

public class HomePresenter extends RxPresenter<HomeContract.IView> implements HomeContract.IPresenter {
    @Inject
    public HomePresenter() {
    }


}
