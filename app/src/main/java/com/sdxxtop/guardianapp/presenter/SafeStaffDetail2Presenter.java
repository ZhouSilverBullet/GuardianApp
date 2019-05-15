package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetail2Contract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class SafeStaffDetail2Presenter extends RxPresenter<SafeStaffDetail2Contract.IView> implements SafeStaffDetail2Contract.IPresenter {
    @Inject
    public SafeStaffDetail2Presenter() {
    }


    public void enterpriseUserdetails(int partId) {
        Params params = new Params();
        params.put("pai", partId);

        Observable<RequestBean<EnterpriseUserdetailsBean>> observable = getEnvirApi().postEnterpriseUserdetails(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EnterpriseUserdetailsBean>() {
            @Override
            public void onSuccess(EnterpriseUserdetailsBean bean) {
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
