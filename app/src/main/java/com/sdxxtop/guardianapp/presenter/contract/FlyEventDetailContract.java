package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;

/**
 * 用来copy使用的
 */
public interface FlyEventDetailContract {
    interface IView extends BaseView {

        void showData(FlyEventDetailBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
