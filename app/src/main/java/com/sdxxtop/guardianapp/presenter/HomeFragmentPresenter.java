package com.sdxxtop.guardianapp.presenter;

import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.presenter.contract.HomeFragmentContract;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.NewsBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.api.ApiService;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeFragmentPresenter extends RxPresenter<HomeFragmentContract.IView> implements HomeFragmentContract.IPresenter {
    @Inject
    public HomeFragmentPresenter() {
    }

    @Override
    public void loadData() {
        ApiService xxApi = RetrofitHelper.getNewsApi();
        Disposable subscribe = xxApi.getData().compose(RxUtils.schedulers())
                .compose(RxUtils.handleResult())
                .subscribe(new Consumer<NewsBean>() {
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {
                        mView.showData(newsBean.title);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.e(" Throwable " + throwable);
                        mView.showError("showError被调用");
                    }
                });

//        Disposable subscribe = xxApi.getData().compose(RxUtils.schedulers())
//                .subscribe(new Consumer<RequestBean>() {
//                    @Override
//                    public void accept(RequestBean requestBean) throws Exception {
//                        if (requestBean.getCode() == 200) {
//                            mView.showData(requestBean.getMsg());
//                        } else {
//                            mView.showError(requestBean.getMsg());
//                        }
//                    }
//                });
        addSubscribe(subscribe);
    }

    @Override
    public void loadSignData() {
        Params params = new Params();
        params.put("ci", 1000208);
        params.put("ui", 50002337);
        params.put("pi", 1);
        Disposable subscribe = RetrofitHelper.getNewsApi().getZhiDian(params.getData())
                .compose(RxUtils.schedulers())
//                .compose(RxUtils.handleResult())
                .subscribe(new Consumer<RequestBean>() {
                    @Override
                    public void accept(RequestBean requestBean) throws Exception {
                        if (requestBean != null) {
                            Logger.e(requestBean.getMsg() + "--- " + requestBean.getCode());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        addSubscribe(subscribe);
    }

    @Override
    public void loadInfo() {

    }
}
