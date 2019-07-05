package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;

/**
 * 用来copy使用的
 */
public interface DeviceDataDetailContract {
    interface IView extends BaseView {

        void showData(DeviceDataBean bean);

        void showListData(DeviceDataBean bean,int pageSize);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
