package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends RxPresenter<HomeContract.IView> implements HomeContract.IPresenter {
    @Inject
    public HomePresenter() {
    }


    public void initApp() {
        Params params = new Params();
        params.put("pi", 1);
        Observable<RequestBean<InitBean>> observable = getEnvirApi().postAppInit(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<InitBean>() {
            @Override
            public void onSuccess(InitBean initBean) {
                mView.showInit(initBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void articleIndex(int position,boolean wasSelected) {
        Params params = new Params();
        Observable<RequestBean<ArticleIndexBean>> observable = getEnvirApi().postArticleIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ArticleIndexBean>() {
            @Override
            public void onSuccess(ArticleIndexBean bean) {
                if (bean!=null){
                    mView.showPermission(bean,position,wasSelected);
                }else{
                    mView.showError("暂无权限");
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }
}
