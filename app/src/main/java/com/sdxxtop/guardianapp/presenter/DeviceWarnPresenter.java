package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EarlyWarningBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.DeviceWarnContract;
import com.sdxxtop.guardianapp.ui.activity.DeviceWarnDetailActivity;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class DeviceWarnPresenter extends RxPresenter<DeviceWarnContract.IView> implements DeviceWarnContract.IPresenter {
    @Inject
    public DeviceWarnPresenter() {
    }


    public void loadData(int earlyId) {
        Params params = new Params();
        params.put("eid", earlyId);

        Observable<RequestBean<EarlyWarningBean>> observable = getEnvirApi().postDeviceEarlyRead(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EarlyWarningBean>() {
            @Override
            public void onSuccess(EarlyWarningBean bean) {
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

    public void deviceModify(int earlyId, int status, String extra) {
        Params params = new Params();
        params.put("eid", earlyId);
        params.put("st", status);
        params.put("et", extra);

        Observable<RequestBean> observable = getEnvirApi().postDeviceModify(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
                if (mView != null) {
                    UIUtils.showToast(bean.getMsg());
                    BaseMvpActivity activity = (DeviceWarnDetailActivity)mView;
                    if (activity!=null){
                        activity.finish();
                    }
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
