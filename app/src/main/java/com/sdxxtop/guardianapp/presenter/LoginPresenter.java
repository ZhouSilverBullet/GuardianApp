package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.LoginContract;

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
