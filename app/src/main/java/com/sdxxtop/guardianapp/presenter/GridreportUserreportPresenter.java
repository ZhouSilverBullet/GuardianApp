package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.GridreportUserreportContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class GridreportUserreportPresenter extends RxPresenter<GridreportUserreportContract.IView> implements GridreportUserreportContract.IPresenter {
    @Inject
    public GridreportUserreportPresenter() {
    }


    public void gridreportUserreport(int part_userid, String startTime, String endTime, int start_page) {
        Params params = new Params();
        params.put("puid", part_userid);
        params.put("sp", start_page);
        params.put("st", start_page);
        params.put("et", start_page);

        Observable<RequestBean<GridreportUserreportBean>> observable = getEnvirApi().postGridreportUserreport(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<GridreportUserreportBean>() {
            @Override
            public void onSuccess(GridreportUserreportBean bean) {
                if (bean != null) {
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
