package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.model.bean.FlyEventListBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.TaskAgentsContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TaskAgentsPresenter extends RxPresenter<TaskAgentsContract.IView> implements TaskAgentsContract.IPresenter {

    @Inject
    public TaskAgentsPresenter() {
    }


    public void loadData(String date, int status, int categoryId) {
        Params params = new Params();
//        st  开始时间     et 结束时间    su状态         cid 分类id
        params.put("su", status);
        params.put("cid", categoryId);

        Observable<RequestBean<FlyEventListBean>> observable = getEnvirApi().postMonthFlyEvent(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<FlyEventListBean>() {
            @Override
            public void onSuccess(FlyEventListBean bean) {
                if (mView != null) {
                    if (bean.month_uav != null) {
                        mView.setMonthUavData(bean.month_uav);
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    /**
     * 获取所选月份的每天事件
     *
     * @param month 月份
     */
    public void getUavData(String month) {
        Params params = new Params();
        params.put("mt", month);

        Observable<RequestBean<FlyEventListBean>> observable = getEnvirApi().postMonthFlyEvent(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<FlyEventListBean>() {
            @Override
            public void onSuccess(FlyEventListBean bean) {
                if (mView != null) {
                    if (bean.month_uav != null) {
                        mView.setMonthUavData(bean.month_uav);
                    }
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void loadData(int page, int type) {
        Params params = new Params();
        params.put("et", type);
        params.put("plid", 1);
        params.put("sp", page);
        Observable<RequestBean<EventIndexBean>> observable = getEnvirApi().postEventIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventIndexBean>() {
            @Override
            public void onSuccess(EventIndexBean eventIndexBean) {
                if (mView != null) {
                    mView.showData(page, eventIndexBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
