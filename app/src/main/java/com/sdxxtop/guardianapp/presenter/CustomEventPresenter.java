package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.CustomEventContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class CustomEventPresenter extends RxPresenter<CustomEventContract.IView> implements CustomEventContract.IPresenter {
    @Inject
    public CustomEventPresenter() {
    }


    public void loadData(int streamId) {
        Params params = new Params();
        params.put("esid", streamId);

        Observable<RequestBean<EventStreamReportBean>> observable = getEnvirApi().postEventStream(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventStreamReportBean>() {
            @Override
            public void onSuccess(EventStreamReportBean bean) {
                if (mView!=null){
                    mView.setData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
