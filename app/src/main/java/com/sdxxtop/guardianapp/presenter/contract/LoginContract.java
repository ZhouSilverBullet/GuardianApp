package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

public interface LoginContract {
    interface IView extends BaseView {
        void loginSuccess();
    }

    interface IPresenter extends BasePresenter<LoginContract.IView> {
        void login();
    }
}
