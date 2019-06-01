package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;

public interface EventReportDetailContract {
    interface IView extends BaseView {
        void readData(EventReadIndexBean eventReadBean);

        void modifyRefresh();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
