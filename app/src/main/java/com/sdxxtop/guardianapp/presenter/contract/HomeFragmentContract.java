package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;

public interface HomeFragmentContract {
    interface IView extends BaseView {
        void showData(MainIndexBean data);

        void showInfo();
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData();

        /**
         * 加载签名的
         */
        void loadSignData();


        void loadInfo();
    }
}
