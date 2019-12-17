package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;

/**
 * 用来copy使用的
 */
public interface CustomEventContract {
    interface IView extends BaseView {

        void setData(EventStreamReportBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
