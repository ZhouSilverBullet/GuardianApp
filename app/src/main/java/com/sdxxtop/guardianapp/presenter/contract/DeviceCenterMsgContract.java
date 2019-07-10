package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;

/**
 * 用来copy使用的
 */
public interface DeviceCenterMsgContract {
    interface IView extends BaseView {

        void showData(UnreadNewslistBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
