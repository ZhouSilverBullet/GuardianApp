package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageParams;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.MineContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class MinePresenter extends RxPresenter<MineContract.IView> implements MineContract.IPresenter {
    @Inject
    public MinePresenter() {
    }


    public void loadData() {
        Params params = new Params();
        Observable<RequestBean<UcenterIndexBean>> observable = getEnvirApi().postUcenterIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<UcenterIndexBean>() {
            @Override
            public void onSuccess(UcenterIndexBean ucenterIndexBean) {
                mView.showList(ucenterIndexBean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void changeIcon(String path) {
        ImageParams params = new ImageParams();
        params.addImagePath("img", new File(path));
        Observable<RequestBean> observable = getEnvirApi().postUcenterModImg(params.getImgData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean ucenterIndexBean) {
                mView.changeIconSuccess();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
