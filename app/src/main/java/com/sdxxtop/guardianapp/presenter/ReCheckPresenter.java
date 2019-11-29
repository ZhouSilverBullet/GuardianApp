package com.sdxxtop.guardianapp.presenter;


import android.app.Activity;

import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ReCheckContract;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.utils.VideoCompressUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class ReCheckPresenter extends RxPresenter<ReCheckContract.IView> implements ReCheckContract.IPresenter {

    private VideoCompressUtil util;

    @Inject
    public ReCheckPresenter() {
    }

    public void patrolHandle(String content, List<File> imagePushPath, List<File> videoPushPath,int patrolId) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("ct", content);
        params.put("pld", patrolId);

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

    public void request(ImageAndVideoParams params){
        ((BaseActivity)mView).showLoadingDialog();
        Observable<RequestBean> observable = getEnvirLongApi().postPatrolHandle(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
                if (mView != null) {
                    mView.showMsg(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mView != null) {
                    UIUtils.showToast(error);
                    mView.showError(error);
                }
            }
        });
        addSubscribe(disposable);
    }
}
