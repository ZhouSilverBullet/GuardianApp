package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EnterpriseTrailBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.PatrolPathContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class PatrolPathPresenter extends RxPresenter<PatrolPathContract.IView> implements PatrolPathContract.IPresenter {
    @Inject
    public PatrolPathPresenter() {
    }


    public void enterpriseTrail(int userid,String start_time) {
        Params params = new Params();
        params.put("puid", userid);
        params.put("st", start_time);

        Observable<RequestBean<EnterpriseTrailBean>> observable = getEnvirApi().postEnterpriseTrail(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EnterpriseTrailBean>() {
            @Override
            public void onSuccess(EnterpriseTrailBean bean) {
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
