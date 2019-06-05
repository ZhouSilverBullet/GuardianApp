package com.sdxxtop.guardianapp.presenter;

import android.app.Activity;

import com.google.gson.internal.LinkedTreeMap;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventReportContract;
import com.sdxxtop.guardianapp.utils.UIUtils;
import com.sdxxtop.guardianapp.utils.VideoCompressUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EventReportPresenter extends RxPresenter<EventReportContract.IView> implements EventReportContract.IPresenter {

    private VideoCompressUtil util;

    @Inject
    public EventReportPresenter() {
    }


    public void pushReport(String title, int pathType, int patrolType,
                           String place, String longitude, String content, List<File> imagePushPath, List<File> videoPushPath) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("tl", title);
        params.put("pt", pathType);
        params.put("plt", patrolType);
        params.put("pl", place);
        params.put("lt", longitude);
        params.put("ct", content);

        params.addImagePathList("img[]", imagePushPath);
        if (videoPushPath!=null&&videoPushPath.size()>0){
            util = new VideoCompressUtil((Activity)mView);
            File file = videoPushPath.get(0);
            util.videoCompress(file.getPath());

            util.setOnVideoCompress(new VideoCompressUtil.OnVideoCompress() {
                @Override
                public void success(String path) {
                    ((BaseMvpActivity)mView).showLoadingDialog();
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
        Observable<RequestBean> observable = getEnvirApi().postEventAdd(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                String eventId = "";
                Object data = requestBean.getData();
                if (data != null) {
                    eventId = (String) ((LinkedTreeMap) data).get("event_id");
                }
                mView.pushSuccess(eventId);
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loadAera() {
        Params params = new Params();
        Observable<RequestBean<ShowPartBean>> observable = getEnvirApi().postEventShowPart(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ShowPartBean>() {
            @Override
            public void onSuccess(ShowPartBean showPartBean) {
//                mView.modifyRefresh();
                List<ShowPartBean.PartBean> part = showPartBean.getPart();
                if (part != null) {
                    mView.showPart(part);
                }
            }

            @Override
            public void onFailure(int code, String error) {
//                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
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
}
