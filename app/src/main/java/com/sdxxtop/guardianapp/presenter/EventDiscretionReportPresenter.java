package com.sdxxtop.guardianapp.presenter;


import android.app.Activity;

import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionReportContract;
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
public class EventDiscretionReportPresenter extends RxPresenter<EventDiscretionReportContract.IView> implements EventDiscretionReportContract.IPresenter {

    private VideoCompressUtil util;

    @Inject
    public EventDiscretionReportPresenter() {
    }


    public void pushReport(int status, String eventTitle, String place, String longitude, String content, String rectify_time, List<File> imagePushPath,
                           List<File> videoPushPath) {

        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("tl", eventTitle);
        params.put("pl", place);
        params.put("lt", longitude);
        params.put("ct", content);
        params.put("rt", rectify_time);
        params.put("sta", status);

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

    public void searchTitle(String title) {
        Params params = new Params();
        params.put("kwd", title);
        Observable<RequestBean<EventSearchTitleBean>> observable = getEnvirApi().postEventSearch(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventSearchTitleBean>() {
            @Override
            public void onSuccess(EventSearchTitleBean Bean) {
                mView.showSearchData(Bean);
            }

            @Override
            public void onFailure(int code, String error) {
//                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void request(ImageAndVideoParams params){
        ((BaseActivity)mView).showLoadingDialog();
        Observable<RequestBean<PatrolAddBean>> observable = getEnvirLongApi().postPatrolAdd(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<PatrolAddBean>() {
            @Override
            public void onSuccess(PatrolAddBean bean) {
                mView.skipDetail(bean);
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
