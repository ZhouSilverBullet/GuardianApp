package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.DeviceDataDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class DeviceDataDetailPresenter extends RxPresenter<DeviceDataDetailContract.IView> implements DeviceDataDetailContract.IPresenter {
    @Inject
    public DeviceDataDetailPresenter() {
    }


    public void loadData(String deviceId, String day, String time) {
        Params params = new Params();
        params.put("deid", deviceId);
        params.put("date", day);
        params.put("intime", time);

        Observable<RequestBean<DeviceDataBean>> observable = getEnvirApi().postDeviceDeviceRead(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<DeviceDataBean>() {
            @Override
            public void onSuccess(DeviceDataBean bean) {
                if (mView != null) {
                    mView.showData(bean);
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

    public void loadListData(String deviceId, String day, String time,int pageSize) {
        Params params = new Params();
        params.put("deid", deviceId);
        params.put("date", day);
        params.put("intime", time);
        params.put("sp", pageSize);

        Observable<RequestBean<DeviceDataBean>> observable = getEnvirApi().postDeviceDeviceReadList(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<DeviceDataBean>() {
            @Override
            public void onSuccess(DeviceDataBean bean) {
                if (mView != null) {
                    mView.showListData(bean,pageSize);
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
