package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GACPContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class GACPPresenter extends RxPresenter<GACPContract.IView> implements GACPContract.IPresenter {
    @Inject
    public GACPPresenter() {
    }


    public void gridreportPatrol(int part_typeid, int start_page) {

        Params params = new Params();
        params.put("ety", part_typeid);
        params.put("sp", start_page);

        Observable<RequestBean<GridreportPatrolBean>> observable = getEnvirApi().postGridreportPatrol(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GridreportPatrolBean>() {
            @Override
            public void onSuccess(GridreportPatrolBean bean) {
                if (bean!=null){
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
