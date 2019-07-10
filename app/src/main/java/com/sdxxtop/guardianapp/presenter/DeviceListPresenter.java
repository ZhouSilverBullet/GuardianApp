package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.DeviceListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.DeviceListContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class DeviceListPresenter extends RxPresenter<DeviceListContract.IView> implements DeviceListContract.IPresenter {
    @Inject
    public DeviceListPresenter() {
    }


    public void loadData(int part_typeid, int type) {
        Params params = new Params();
        params.put("ty", type);
        params.put("ety", part_typeid);

        Observable<RequestBean<DeviceListBean>> observable = getEnvirApi().postDeviceDeviceList(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<DeviceListBean>() {
            @Override
            public void onSuccess(DeviceListBean bean) {
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
}
