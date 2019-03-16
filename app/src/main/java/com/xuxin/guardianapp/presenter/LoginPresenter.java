package com.xuxin.guardianapp.presenter;

import com.xuxin.guardianapp.base.RxPresenter;
import com.xuxin.guardianapp.presenter.contract.LoginContract;

import javax.inject.Inject;

public class LoginPresenter extends RxPresenter<LoginContract.IView> implements LoginContract.IPresenter {
    @Inject
    public LoginPresenter() {
    }


    @Override
    public void login() {
        mView.loginSuccess();
    }
}
