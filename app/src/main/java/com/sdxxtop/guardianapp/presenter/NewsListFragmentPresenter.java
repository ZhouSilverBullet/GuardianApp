package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.AllarticleBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.NewsListFragmentContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class NewsListFragmentPresenter extends RxPresenter<NewsListFragmentContract.IView> implements NewsListFragmentContract.IPresenter {
    @Inject
    public NewsListFragmentPresenter() {
    }

    @Override
    public void loadData(int count, int type) {
        Params params = new Params();
        params.put("an", count);
        params.put("te", type);

        Observable<RequestBean<AllarticleBean>> observable = getEnvirApi().postAllarticleData(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<AllarticleBean>() {
            @Override
            public void onSuccess(AllarticleBean mainIndexBean) {
                if (mView != null) {
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
}
