package com.xuxin.guardianapp.presenter;

import com.xuxin.guardianapp.base.RxPresenter;
import com.xuxin.guardianapp.presenter.contract.NewsListFragmentContract;

import javax.inject.Inject;

public class NewsListFragmentPresenter extends RxPresenter<NewsListFragmentContract.IView> implements NewsListFragmentContract.IPresenter {
    @Inject
    public NewsListFragmentPresenter() {
    }

    @Override
    public void loadData(String value) {
        mView.showData(value);
    }
}
