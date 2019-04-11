package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
import com.sdxxtop.guardianapp.presenter.contract.TaskAgentsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TaskAgentsPresenter extends RxPresenter<TaskAgentsContract.IView> implements TaskAgentsContract.IPresenter {
    @Inject
    public TaskAgentsPresenter() {
    }


    @Override
    public void loadData(int page) {
        Params params = new Params();
        params.put("et", "1");
        Observable<RequestBean<EventIndexBean>> observable = getEnvirApi().postEventIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventIndexBean>() {
            @Override
            public void onSuccess(EventIndexBean eventIndexBean) {
                mView.showData(page, eventIndexBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
