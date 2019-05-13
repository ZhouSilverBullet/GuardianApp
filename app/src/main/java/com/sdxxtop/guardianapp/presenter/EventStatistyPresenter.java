package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventStatistyContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 事件统计
 */
public class EventStatistyPresenter extends RxPresenter<EventStatistyContract.IView> implements EventStatistyContract.IPresenter {
    @Inject
    public EventStatistyPresenter() {
    }


    public void eventlist(int event_type,int part_typeid) {
        Params params = new Params();
        params.put("pt", part_typeid);
        params.put("st", "");
        params.put("et", "");
        params.put("ety", event_type);

        Observable<RequestBean<EventListBean>> observable = getEnvirApi().postEventlist(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventListBean>() {
            @Override
            public void onSuccess(EventListBean listBean) {
                mView.showListData(listBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
