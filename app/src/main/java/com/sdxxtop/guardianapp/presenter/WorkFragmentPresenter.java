package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.WorkFragmentContract;
import com.sdxxtop.guardianapp.ui.fragment.SectionEventFragment;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 用来copy使用的
 */
public class WorkFragmentPresenter extends RxPresenter<WorkFragmentContract.IView> implements WorkFragmentContract.IPresenter {
    @Inject
    public WorkFragmentPresenter() {
    }


    public void loadWorkIndex() {
        Params params = new Params();
        Observable<RequestBean<WorkIndexBean>> observable = getEnvirApi().postWorkIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<WorkIndexBean>() {
            @Override
            public void onSuccess(WorkIndexBean bean) {
                if (mView != null) {
                    mView.showIndex(bean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                ((SectionEventFragment)mView).closeLoadingDialog();
                mView.showError(error);
            }
        });
        addSubscribe(disposable);
    }
}
