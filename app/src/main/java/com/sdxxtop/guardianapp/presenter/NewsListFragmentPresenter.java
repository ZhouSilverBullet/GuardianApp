package com.sdxxtop.guardianapp.presenter;

import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.model.bean.LearnNewsBean;
import com.sdxxtop.guardianapp.presenter.contract.NewsListFragmentContract;
import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.net.RetrofitHelper;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsListFragmentPresenter extends RxPresenter<NewsListFragmentContract.IView> implements NewsListFragmentContract.IPresenter {
    @Inject
    public NewsListFragmentPresenter() {
    }

    @Override
    public void loadData(int count,int type) {
        Params params = new Params();
        params.put("an", count);
        params.put("te", type);
        Disposable subscribe = RetrofitHelper.getNewsApi().getAllArticle(params.getData())
                .compose(RxUtils.schedulers())
//                .compose(RxUtils.handleResult())
                .subscribe(new Consumer<RequestBean>() {
                    @Override
                    public void accept(RequestBean requestBean) throws Exception {
                        if (requestBean != null) {
                            if (requestBean.getCode()==200){
                                mView.showData((List<LearnNewsBean>) requestBean.getData());
                            }else{
                                UIUtils.showToast(requestBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError("");
                        Logger.e( "--- " + throwable.toString());
                    }
                });
        addSubscribe(subscribe);
    }
}
