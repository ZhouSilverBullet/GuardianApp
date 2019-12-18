package com.sdxxtop.guardianapp.presenter;

import android.app.Activity;

import com.google.gson.internal.LinkedTreeMap;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.EventModeBean;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
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


    public void pushReport(
            String title, int pathType,
            String place, String longitude, String content,
            List<File> imagePushPath, List<File> videoPushPath,
            String positionDesc, int categoryId,
            int streamType

            , int streamId) {
        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("tl", title);
        params.put("pt", pathType);
        params.put("plt", 1);
        params.put("pl", place);
        params.put("lt", longitude);
        params.put("ct", content);
        params.put("spt", positionDesc);
        params.put("cgid", categoryId);
        params.put("esid", streamId);

        params.addImagePathList("img[]", imagePushPath);
        if (videoPushPath != null && videoPushPath.size() > 0) {
            util = new VideoCompressUtil((Activity) mView);
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
        } else {
            request(params);
        }
    }

    private void request(ImageAndVideoParams params) {
        ((BaseActivity) mView).showLoadingDialog();
        Observable<RequestBean> observable = getEnvirLongApi().postEventAdd(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                if (mView == null) {
                    return;
                }
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
                if (mView != null) {
                    mView.showError(error);
                }
            }
        });
        addSubscribe(disposable);
    }

    public void keywordMatch(String keyword, int keyword_id) {
        Params params = new Params();
        params.put("kd", keyword);
        params.put("cki", keyword_id);
        Observable<RequestBean<ShowPartBean>> observable = getEnvirApi().postEventShowPart(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ShowPartBean>() {
            @Override
            public void onSuccess(ShowPartBean showPartBean) {
                if (mView != null) {
                    mView.showKeywordInfo(showPartBean, keyword_id);
                }
            }

            @Override
            public void onFailure(int code, String error) {
//                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loadSector() {
        Params params = new Params();
        Observable<RequestBean<EventShowBean>> observable = getEnvirApi().postEventSector(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventShowBean>() {
            @Override
            public void onSuccess(EventShowBean bean) {
                if (mView != null) {
                    mView.showEventBean(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void searchTitle() {
        Params params = new Params();
        Observable<RequestBean<EventSearchTitleBean>> observable = getEnvirApi().postEventSearch(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventSearchTitleBean>() {
            @Override
            public void onSuccess(EventSearchTitleBean bean) {
                if (mView != null) {
                    mView.showSearchData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loadData(int streamId) {
        Params params = new Params();
        params.put("esid", streamId);

        Observable<RequestBean<EventStreamReportBean>> observable = getEnvirApi().postEventStream(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventStreamReportBean>() {
            @Override
            public void onSuccess(EventStreamReportBean bean) {
                if (mView != null) {
                    mView.setPermissionInfo(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

    public void eventMode(int mSelectPartId) {
        Params params = new Params();
        params.put("pt", mSelectPartId);
        Observable<RequestBean<EventModeBean>> observable = getEnvirApi().postEventMode(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<EventModeBean>() {
            @Override
            public void onSuccess(EventModeBean bean) {
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
