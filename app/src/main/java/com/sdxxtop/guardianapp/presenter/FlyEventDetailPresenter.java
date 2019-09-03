package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class FlyEventDetailPresenter extends RxPresenter<FlyEventDetailContract.IView> implements FlyEventDetailContract.IPresenter {
    @Inject
    public FlyEventDetailPresenter() {
    }


    public void loadData(int eventId, String searchText) {
        Params params = new Params();
        params.put("ti", eventId);
        params.put("pl", searchText);

        Observable<RequestBean<FlyEventDetailBean>> observable = getEnvirApi().postFlyEventDetail(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<FlyEventDetailBean>() {
            @Override
            public void onSuccess(FlyEventDetailBean bean) {
                if (mView != null) {
                    mView.showData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
