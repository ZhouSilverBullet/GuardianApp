package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.CenterMessageContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class CenterMessagePresenter extends RxPresenter<CenterMessageContract.IView> implements CenterMessageContract.IPresenter {
    @Inject
    public CenterMessagePresenter() {
    }


    public void unreadIndex() {
        Params params = new Params();
        params.put("plid", 1);
        Observable<RequestBean<UnreadIndexBean>> observable = getEnvirApi().postUnreadIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<UnreadIndexBean>() {
            @Override
            public void onSuccess(UnreadIndexBean bean) {
                if (mView != null) {
                    mView.showData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
