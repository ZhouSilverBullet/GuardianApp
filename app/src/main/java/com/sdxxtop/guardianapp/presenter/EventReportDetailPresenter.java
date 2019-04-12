package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventReadBean;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EventReportDetailPresenter extends RxPresenter<EventReportDetailContract.IView> implements EventReportDetailContract.IPresenter {
    @Inject
    public EventReportDetailPresenter() {
    }

    public void loadData(String eventId) {
        Params params = new Params();
        params.put("ei", eventId);
        Observable<RequestBean<EventReadBean>> observable = getEnvirApi().postEventRead(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventReadBean>() {
            @Override
            public void onSuccess(EventReadBean eventReadBean) {
                mView.readData(eventReadBean);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void modify(String eventId, int status, String extra) {
        Params params = new Params();
        params.put("ei", eventId);
        params.put("st",status);
        params.put("et", extra);
        Observable<RequestBean> observable = getEnvirApi().postEventModify(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.modifyRefresh();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

}
