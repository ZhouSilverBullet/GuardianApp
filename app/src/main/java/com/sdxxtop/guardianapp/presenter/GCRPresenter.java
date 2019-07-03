package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GCRContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * GrantCompanyReportPresenter
 */
public class GCRPresenter extends RxPresenter<GCRContract.IView> implements GCRContract.IPresenter {
    @Inject
    public GCRPresenter() {
    }


    public void enterpriseIndex(int part_typeid) {
        Params params = new Params();
        params.put("ety", part_typeid);

        Observable<RequestBean<EnterpriseIndexBean>> observable = getEnvirApi().postEnterpriseIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EnterpriseIndexBean>() {
            @Override
            public void onSuccess(EnterpriseIndexBean bean) {
                if (mView != null) {
                    mView.showData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);

    }
}
