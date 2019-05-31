package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ERDSecondPresenter extends RxPresenter<ERDSecondContract.IView> implements ERDSecondContract.IPresenter {
    @Inject
    public ERDSecondPresenter() {
    }

    public void modify(String eventId, int status, String extra, List<File> imagePushPath, List<File> VideoPushPath) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("ei", eventId);
        params.put("st", status);
        params.put("et", extra);

        params.addImagePathList("img[]", imagePushPath);
        if (VideoPushPath != null && VideoPushPath.size() > 0) {
            params.addCompressVideoPath("video", VideoPushPath.get(0));
        }

        Observable<RequestBean> observable = getEnvirApi().postEventFailed(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                mView.modifyRefresh();
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
