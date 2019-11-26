package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;

/**
 * 用来copy使用的
 */
public interface EventDiscretionReportContract {
    interface IView extends BaseView {

        void skipDetail(PatrolAddBean bean);

        void showSearchData(EventSearchTitleBean bean);

        void showKeywordInfo(ShowPartBean showPartBean, int keyword_id);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
