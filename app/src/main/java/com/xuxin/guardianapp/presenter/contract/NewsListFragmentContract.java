package com.xuxin.guardianapp.presenter.contract;

import com.xuxin.guardianapp.base.BasePresenter;
import com.xuxin.guardianapp.base.BaseView;

public interface NewsListFragmentContract {
    interface IView extends BaseView {
        void showData(String value);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(String value);
    }


}
