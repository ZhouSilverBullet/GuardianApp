package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionListContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class EventDiscretionListPresenter extends RxPresenter<EventDiscretionListContract.IView> implements EventDiscretionListContract.IPresenter {
    @Inject
    public EventDiscretionListPresenter() {
    }


    public void loadData(int size) {
        Params params = new Params();
        params.put("sp", size);
        Observable<RequestBean<EventDiscretionListBean>> observable = getEnvirApi().postPatrolIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventDiscretionListBean>() {
            @Override
            public void onSuccess(EventDiscretionListBean bean) {
                mView.showData(size,bean);
            }

            @Override
            public void onFailure(int code, String error) {
                if (mView != null) {
                    mView.showError(error);
                }
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
