package com.sdxxtop.guardianapp.base;

import com.sdxxtop.guardianapp.model.http.api.ApiService;
import com.sdxxtop.guardianapp.model.http.api.EnvirApiService;
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void attachView(T t) {
        this.mView = t;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public EnvirApiService getEnvirApi() {
        return RetrofitHelper.getEnvirApi();
    }

    public EnvirApiService getEnvirLongApi() {
        return RetrofitHelper.getLongEnvirApi();
    }

    public ApiService getApi() {
        return RetrofitHelper.getNewsApi();
    }
}
