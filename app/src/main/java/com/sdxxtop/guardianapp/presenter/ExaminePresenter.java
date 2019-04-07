package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ExamineContract;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;

import javax.inject.Inject;

public class ExaminePresenter extends RxPresenter<ExamineContract.IView> implements ExamineContract.IPresenter {
    @Inject
    public ExaminePresenter() {
    }

    public void loadData() {
        mView.showData("");
    }

}
