package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.DeviceMapBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.MonitorMapContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class MonitorMapPresenter extends RxPresenter<MonitorMapContract.IView> implements MonitorMapContract.IPresenter {
    @Inject
    public MonitorMapPresenter() {
    }


    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<DeviceMapBean>> observable = getEnvirApi().postDeviceDeviceMap(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<DeviceMapBean>() {
            @Override
            public void onSuccess(DeviceMapBean bean) {
                if (mView != null) {
                    mView.showMapInfo(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mView != null) {
                    mView.showError(error);
                }
            }
        });
        addSubscribe(disposable);
    }
}
