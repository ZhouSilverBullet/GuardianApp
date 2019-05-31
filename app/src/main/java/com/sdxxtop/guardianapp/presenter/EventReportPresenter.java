package com.sdxxtop.guardianapp.presenter;

import com.google.gson.internal.LinkedTreeMap;
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

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EventReportPresenter extends RxPresenter<EventReportContract.IView> implements EventReportContract.IPresenter {
    @Inject
    public EventReportPresenter() {
    }


    public void pushReport(String title, int pathType, int patrolType,
                           String place, String longitude, String content, List<File> imagePushPath, List<File> vedioPushPath) {
        ImageAndVideoParams imageParams = new ImageAndVideoParams();
        imageParams.put("tl", title);
        imageParams.put("pt", pathType);
        imageParams.put("plt", patrolType);
        imageParams.put("pl", place);
        imageParams.put("lt", longitude);
        imageParams.put("ct", content);

        imageParams.addImagePathList("img[]", imagePushPath);
        if (vedioPushPath != null && vedioPushPath.size() > 0) {
            imageParams.addCompressVideoPath("video", vedioPushPath.get(0));
        }

        Observable<RequestBean> observable = getEnvirApi().postEventAdd(imageParams.getImgAndVideoData());
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
