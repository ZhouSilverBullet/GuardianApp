package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.LoginBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.LoginContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends RxPresenter<LoginContract.IView> implements LoginContract.IPresenter {
    @Inject
    public LoginPresenter() {
    }


    @Override
    public void login(String mobile, String authCode, String partId) {
        Params params = new Params();
        params.put("mb", mobile);
        params.put("ac", authCode);
        params.put("pi", partId);
        Observable<RequestBean<LoginBean>> requestBeanObservable = getEnvirApi().postLoginMobileLogin(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(requestBeanObservable, new IRequestCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                if (mView != null) {
                    mView.loginSuccess(loginBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void sendCode(String mobile) {
        Params params = new Params();
        params.put("mb", mobile);

        Observable<RequestBean> observable = getEnvirApi().postLoginSendCode(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                if (mView != null) {
                    mView.sendCodeSuccess();
                    UIUtils.showToast("发送成功");
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mView != null) {
                    mView.sendCodeError();
                    UIUtils.showToast(error);
                }
            }
        });

        addSubscribe(disposable);
    }


}
