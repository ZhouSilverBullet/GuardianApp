package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.model.bean.EventStreamDetailBean;

public interface ProblemGJDetailContract {
    interface IView extends BaseView {
        void readData(EventReadIndexBean eventReadBean);

        void readNewData(EventStreamDetailBean bean);

        void modifyRefresh();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
