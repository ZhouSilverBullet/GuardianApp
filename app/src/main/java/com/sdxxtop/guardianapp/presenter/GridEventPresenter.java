package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.GridEventCountBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GridEventContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class GridEventPresenter extends RxPresenter<GridEventContract.IView> implements GridEventContract.IPresenter {
    @Inject
    public GridEventPresenter() {
    }


    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<GridEventCountBean>> observable = getEnvirApi().postGridEventCount(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GridEventCountBean>() {
            @Override
            public void onSuccess(GridEventCountBean bean) {
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
}
