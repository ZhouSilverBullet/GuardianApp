package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.CopyContract;
import com.sdxxtop.guardianapp.presenter.contract.FaceLivenessContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class FaceLivenessPresenter extends RxPresenter<FaceLivenessContract.IView> implements FaceLivenessContract.IPresenter {

    public FaceLivenessPresenter() {
    }

    public void face(String lanLan, String address) {
        Params params = new Params();
        params.put("slt", lanLan);
        params.put("ad", address);
        Observable<RequestBean> observable = getEnvirApi().postMainSign(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.faceSuccess(address);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
