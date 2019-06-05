package com.sdxxtop.guardianapp.presenter;


import android.app.Activity;

import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.utils.VideoCompressUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ERDSecondPresenter extends RxPresenter<ERDSecondContract.IView> implements ERDSecondContract.IPresenter {

    private VideoCompressUtil util;

    @Inject
    public ERDSecondPresenter() {
    }

    public void modify(String eventId, int status, String extra, List<File> imagePushPath, List<File> videoPushPath) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("ei", eventId);
        params.put("st", status);
        params.put("et", extra);

        params.addImagePathList("img[]", imagePushPath);
        if (videoPushPath!=null&&videoPushPath.size()>0){
            util = new VideoCompressUtil((Activity)mView);
            File file = videoPushPath.get(0);
            util.videoCompress(file.getPath());

            util.setOnVideoCompress(new VideoCompressUtil.OnVideoCompress() {
                @Override
                public void success(String path) {
                    params.addCompressVideoPath("video", new File(path));
                    request(params);
                }

                @Override
                public void fail() {
                    UIUtils.showToast("压缩失败,请重新尝试");
                }
            });
        }else{
            request(params);
        }
    }

    private void request(ImageAndVideoParams params) {
        ((BaseActivity)mView).showLoadingDialog();
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
