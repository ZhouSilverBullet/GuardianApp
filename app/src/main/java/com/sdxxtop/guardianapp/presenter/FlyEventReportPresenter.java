package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.FlyEventPartBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventReportContract;
import com.sdxxtop.guardianapp.ui.activity.FlyEventReportActivity;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class FlyEventReportPresenter extends RxPresenter<FlyEventReportContract.IView> implements FlyEventReportContract.IPresenter {
    @Inject
    public FlyEventReportPresenter() {
    }

    public void getPart() {
        Params params = new Params();

        Observable<RequestBean<FlyEventPartBean>> observable = getEnvirApi().postFlyEventPartBean(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<FlyEventPartBean>() {
            @Override
            public void onSuccess(FlyEventPartBean bean) {
                if (mView != null) {
                    mView.showPart(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void reportEvent(String title, int mSelectPartId, int mSelectUavId, String longitude, String reportAddress, String contentText) {
        Params params = new Params();
        params.put("tl",title);
        params.put("pt",mSelectPartId);
        params.put("lt",longitude);
        params.put("pl",reportAddress);
        params.put("ct",contentText);
        params.put("uav",mSelectUavId);

        Observable<RequestBean> observable = getEnvirApi().postFlyEventAdd(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
                if (mView != null) {
                    if (bean.getCode()==200){
                        UIUtils.showToast("上报成功");
                        ((FlyEventReportActivity)mView).finish();
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
