package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.FlyEventPartBean;

/**
 * 用来copy使用的
 */
public interface FlyEventReportContract {
    interface IView extends BaseView {

        void showPart(FlyEventPartBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
