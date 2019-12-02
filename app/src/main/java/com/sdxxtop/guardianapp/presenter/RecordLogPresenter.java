package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RecordLogBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.RecordLogContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class RecordLogPresenter extends RxPresenter<RecordLogContract.IView> implements RecordLogContract.IPresenter {
    @Inject
    public RecordLogPresenter() {
    }


    public void loadLog(String eventId) {
        Params params = new Params();
        params.put("ei", eventId);

        Observable<RequestBean<RecordLogBean>> observable = getEnvirApi().postEventLog(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<RecordLogBean>() {
            @Override
            public void onSuccess(RecordLogBean bean) {
                if (mView != null) {
                    mView.showData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
