package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.MainIndexBeanNew;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.HomeFragmentContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomeFragmentPresenter extends RxPresenter<HomeFragmentContract.IView> implements HomeFragmentContract.IPresenter {
    @Inject
    public HomeFragmentPresenter() {
    }

    @Override
    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<MainIndexBeanNew>> observable = getEnvirApi().postMainIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<MainIndexBeanNew>() {
            @Override
            public void onSuccess(MainIndexBeanNew mainIndexBean) {
                if (mView!=null){
                    mView.showData(mainIndexBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void loadSignData() {
    }

    @Override
    public void loadInfo() {

    }
}
