package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;

public interface EventReportContract {
    interface IView extends BaseView {
        void pushSuccess(String eventId);

        void showKeywordInfo(ShowPartBean par,int keyword_id);

        void showSearchData(EventSearchTitleBean bean);

        void showEventBean(EventShowBean bean);

        void setPermissionInfo(EventStreamReportBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
