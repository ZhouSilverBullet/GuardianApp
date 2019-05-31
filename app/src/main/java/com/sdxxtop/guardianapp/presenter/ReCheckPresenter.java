package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ReCheckContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class ReCheckPresenter extends RxPresenter<ReCheckContract.IView> implements ReCheckContract.IPresenter {
    @Inject
    public ReCheckPresenter() {
    }

    public void patrolHandle(String content, List<File> imagePushPath, List<File> videoPushPath,int patrolId) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("ct", content);
        params.put("pld", patrolId);

        params.addImagePathList("img[]", imagePushPath);

        if (videoPushPath != null && videoPushPath.size() == 1) {
            File file = videoPushPath.get(0);
            params.addCompressVideoPath("video", file);
        }

        Observable<RequestBean> observable = getEnvirApi().postPatrolHandle(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean bean) {
                mView.showMsg(bean);
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
