package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EarlyWarningBean;

/**
 * 用来copy使用的
 */
public interface DeviceWarnContract {
    interface IView extends BaseView {

        void showData(EarlyWarningBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
