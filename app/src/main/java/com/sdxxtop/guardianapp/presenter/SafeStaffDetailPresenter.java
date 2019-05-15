package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class SafeStaffDetailPresenter extends RxPresenter<SafeStaffDetailContract.IView> implements SafeStaffDetailContract.IPresenter {

    @Inject
    public SafeStaffDetailPresenter() {

    }

    public void enterpriseSecurity(int id, String startTime, String endTime) {
        Params params = new Params();
        params.put("pai", id);
        params.put("st", startTime);
        params.put("et", endTime);

        Observable<RequestBean<EnterpriseSecurityBean>> observable = getEnvirApi().postEnterpriseSecurity(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EnterpriseSecurityBean>() {
            @Override
            public void onSuccess(EnterpriseSecurityBean bean) {
                mView.showData(bean);
            }

            @Override
            public void onFailure(int code, String error) {
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
