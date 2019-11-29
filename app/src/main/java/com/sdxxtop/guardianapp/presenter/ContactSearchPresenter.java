package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ContactSearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ContactSearchPresenter extends RxPresenter<ContactSearchContract.IView> implements ContactSearchContract.IPresenter {
    @Inject
    public ContactSearchPresenter() {
    }

    public void loadData(String key) {
        Params params = new Params();
        params.put("sh", key);
        Observable<RequestBean<ContactIndexBean>> observable = getEnvirApi().postContactSearch(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ContactIndexBean>() {
            @Override
            public void onSuccess(ContactIndexBean contactIndexBean) {
                if (mView != null) {
                    List<ContactIndexBean.ContactBean> user = contactIndexBean.getUser();
                    mView.showList(user);
                }
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });
        addSubscribe(disposable);
    }

}
