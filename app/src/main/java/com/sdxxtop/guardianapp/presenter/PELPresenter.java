package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * PEL:PartEventList简写
 */
public class PELPresenter extends RxPresenter<PELContract.IView> implements PELContract.IPresenter {
    @Inject
    public PELPresenter() {
    }


    public void postPartEventList(int start_page, String part_typeid, String startTime, String endTime, int event_type) {
        Params params = new Params();
        params.put("sp", start_page);
        params.put("pt", part_typeid);
        params.put("ety", event_type);
        params.put("st", startTime);
        params.put("et", endTime);

        Observable<RequestBean<PartEventListBean>> observable = getEnvirApi().postPartEventList(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<PartEventListBean>() {
            @Override
            public void onSuccess(PartEventListBean bean) {
                mView.showData(bean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
