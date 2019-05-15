package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GACEContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class GACEPresenter extends RxPresenter<GACEContract.IView> implements GACEContract.IPresenter {
    @Inject
    public GACEPresenter() {
    }


    public void enterpriseCompany(int part_typeid,int parent_id) {
        Params params = new Params();
        params.put("ety",part_typeid);
        params.put("pai",parent_id);

        Observable<RequestBean<EnterpriseCompanyBean>> observable = getEnvirApi().postEnterpriseCompany(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EnterpriseCompanyBean>() {
            @Override
            public void onSuccess(EnterpriseCompanyBean bean) {
                mView.showData(bean);
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
        addSubscribe(disposable);
    }
}
