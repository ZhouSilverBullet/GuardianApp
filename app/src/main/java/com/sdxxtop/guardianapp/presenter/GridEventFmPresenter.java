package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.GridEventListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GridEventFmContract;
import com.sdxxtop.guardianapp.ui.fragment.SectionEventFragment;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class GridEventFmPresenter extends RxPresenter<GridEventFmContract.IView> implements GridEventFmContract.IPresenter {
    @Inject
    public GridEventFmPresenter() {
    }


    public void loadData(int pathType, int type, int start_page) {
        String pathRes = "solveList";
        switch (pathType) {
            case 0:
                pathRes = "solveList";
                break;
            case 1:
                pathRes = "reportlist";
                break;
            case 2:
                pathRes = "claimList";
                break;
            case 3:
                pathRes = "partollist";
                break;
        }
        Params params = new Params();
        params.put("ty", type);
        params.put("sp", start_page);
        Observable<RequestBean<GridEventListBean>> observable = getEnvirApi().postGridEventList(pathRes, params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GridEventListBean>() {
            @Override
            public void onSuccess(GridEventListBean bean) {
                if (mView != null && bean != null) {
                    mView.showData(bean, pathType, start_page);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                ((SectionEventFragment) mView).closeLoadingDialog();
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
