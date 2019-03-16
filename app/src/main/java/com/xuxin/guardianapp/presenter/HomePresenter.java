package com.xuxin.guardianapp.presenter;


import com.xuxin.guardianapp.base.RxPresenter;
import com.xuxin.guardianapp.presenter.contract.HomeContract;

import javax.inject.Inject;

public class HomePresenter extends RxPresenter<HomeContract.IView> implements HomeContract.IPresenter {
    @Inject
    public HomePresenter() {
    }


}
