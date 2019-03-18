package com.xuxin.guardianapp.presenter.contract;

import com.xuxin.guardianapp.base.BasePresenter;
import com.xuxin.guardianapp.base.BaseView;

public interface HomeFragmentContract {
    interface IView extends BaseView {
        void showData(String data);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData();

        /**
         * 加载签名的
         */
        void loadSignData();
    }
}
