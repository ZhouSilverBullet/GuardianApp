package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.KqstDayBean;
import com.sdxxtop.guardianapp.model.bean.KqstMonthBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.KQST_fmContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class KQST_fmPresenter extends RxPresenter<KQST_fmContract.IView> implements KQST_fmContract.IPresenter {

    @Inject
    public KQST_fmPresenter() {
    }

    public void loadKqstInfo(int year, int month) {
        Params params = new Params();
        params.put("yr",year);
        params.put("mh",month);
        Observable<RequestBean<KqstMonthBean>> observable = getEnvirApi().kqstDataInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<KqstMonthBean>() {
            @Override
            public void onSuccess(KqstMonthBean bean) {
                if (mView != null) {
                    if (bean != null) {
                        mView.showMonthData(bean);
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {
            }
        });
        addSubscribe(disposable);
    }

    public void loadDayInfo(String day) {
        Params params = new Params();
        params.put("sd",day);
        Observable<RequestBean<KqstDayBean>> observable = getEnvirApi().kqtjDayInfo(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<KqstDayBean>() {
            @Override
            public void onSuccess(KqstDayBean bean) {
                if (mView != null) {
                    if (bean != null) {
                        mView.showDayInfo(bean);
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
