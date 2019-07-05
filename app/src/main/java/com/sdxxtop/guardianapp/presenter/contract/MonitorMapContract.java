package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.DeviceMapBean;

/**
 * 用来copy使用的
 */
public interface MonitorMapContract {
    interface IView extends BaseView {

        void showMapInfo(DeviceMapBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
