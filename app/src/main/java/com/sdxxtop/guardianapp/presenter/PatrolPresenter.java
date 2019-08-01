package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.model.bean.TrackPointBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class PatrolPresenter extends RxPresenter<PatrolContract.IView> implements PatrolContract.IPresenter {
    @Inject
    public PatrolPresenter() {
    }


    public void loadData(String date,int type) {
        Params params = new Params();
        params.put("sd", date);
        params.put("tp", type);
        Observable<RequestBean<SignLogBean>> observable = getEnvirApi().postMainSignLog(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<SignLogBean>() {
            @Override
            public void onSuccess(SignLogBean signLogBean) {
                if (mView != null) {
                    mView.showData(signLogBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }

    public void loadTrack(String date,int type) {
        Params params = new Params();
        params.put("sd", date);
        params.put("tp", type);
        Observable<RequestBean<TrackPointBean>> observable = getEnvirApi().postMoreTrack(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<TrackPointBean>() {
            @Override
            public void onSuccess(TrackPointBean bean) {
                if (mView != null) {
                    mView.showTrackData(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
