package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.GridreportIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GGRContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * GrantGridReportPresenter
 */
public class GGRPresenter extends RxPresenter<GGRContract.IView> implements GGRContract.IPresenter {
    @Inject
    public GGRPresenter() {
    }


    public void gridreportIndex(int part_typeid) {
        Params params = new Params();
        params.put("ety", part_typeid);

        Observable<RequestBean<GridreportIndexBean>> observable = getEnvirApi().postGridreportIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GridreportIndexBean>() {
            @Override
            public void onSuccess(GridreportIndexBean bean) {
                mView.showData(bean);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }
}
