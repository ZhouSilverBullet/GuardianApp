package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactContract;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;

import javax.inject.Inject;

public class PatrolPresenter extends RxPresenter<PatrolContract.IView> implements PatrolContract.IPresenter {
    @Inject
    public PatrolPresenter() {
    }


    public void loadData() {

    }


}
