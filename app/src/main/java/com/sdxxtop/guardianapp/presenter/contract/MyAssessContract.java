package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

public interface MyAssessContract {
    interface IView extends BaseView {
    }

    interface IPresenter extends BasePresenter<MyAssessContract.IView> {

    }
}
