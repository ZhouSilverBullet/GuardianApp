package com.xuxin.guardianapp.presenter.contract;

import com.xuxin.guardianapp.base.BasePresenter;
import com.xuxin.guardianapp.base.BaseView;

public interface LoginContract {
    interface IView extends BaseView {
        void loginSuccess();
    }

    interface IPresenter extends BasePresenter<LoginContract.IView> {
        void login();
    }
}
