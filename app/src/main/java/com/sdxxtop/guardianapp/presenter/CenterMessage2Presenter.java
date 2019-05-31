package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.CenterMessage2Contract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class CenterMessage2Presenter extends RxPresenter<CenterMessage2Contract.IView> implements CenterMessage2Contract.IPresenter {
    @Inject
    public CenterMessage2Presenter() {
    }


    public void unreadNewslist(String name) {
        Params params = new Params();
        params.put("plid", 1);
        Observable<RequestBean> observable = getEnvirApi().postUnreadNewslist(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public int getType(String name) {
        int type = 1;
        if ("代办任务".equals(name)) {
            type=1;
        }
        return type;
    }
}
