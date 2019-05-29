package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.ImageAndVideoParams;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionReportContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class EventDiscretionReportPresenter extends RxPresenter<EventDiscretionReportContract.IView> implements EventDiscretionReportContract.IPresenter {
    @Inject
    public EventDiscretionReportPresenter() {
    }


    public void pushReport(String eventTitle, String place, String longitude, String content, String rectify_time, List<File> imagePushPath,List<File> videoPushPath) {

        ImageAndVideoParams params = new ImageAndVideoParams();
        params.put("tl", eventTitle);
        params.put("pl", place);
        params.put("lt", longitude);
        params.put("ct", content);
        params.put("rt", rectify_time);

        params.addImagePathList("img[]", imagePushPath);
        if (videoPushPath!=null&&videoPushPath.size()==1){
            params.addVideoPathList("video", videoPushPath);
        }

        Observable<RequestBean> observable = getEnvirApi().postPatrolAdd(params.getImgAndVideoData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean requestBean) {
                UIUtils.showToast("success");
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
