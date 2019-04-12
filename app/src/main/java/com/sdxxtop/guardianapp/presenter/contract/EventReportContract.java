package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

public interface EventReportContract {
    interface IView extends BaseView {
        void pushSuccess(String eventId);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
