package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;

/**
 * 用来copy使用的
 */
public interface DataMonitoringContract {
    interface IView extends BaseView {

        void showData(AuthDataBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
