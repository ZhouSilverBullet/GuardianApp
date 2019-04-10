package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.app.Constants;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.SplashContract;
import com.sdxxtop.guardianapp.utils.SpUtil;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashPresenter extends RxPresenter<SplashContract.IView> implements SplashContract.IPresenter {
    @Inject
    public SplashPresenter() {
    }

    public void autoLogin() {
        Params params = new Params();
        params.put("at", SpUtil.getString(Constants.AUTO_TOKEN));
        Observable<RequestBean<AutoLoginBean>> observable = getEnvirApi().postLoginAutoLogin(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<AutoLoginBean>() {
            @Override
            public void onSuccess(AutoLoginBean autoLoginBean) {
                handleData(autoLoginBean);
                mView.autoSuccess(autoLoginBean);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    private void handleData(AutoLoginBean autoLoginBean) {

    }
}
