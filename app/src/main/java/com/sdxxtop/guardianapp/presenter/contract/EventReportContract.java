package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;

import java.util.List;

public interface EventReportContract {
    interface IView extends BaseView {
        void pushSuccess(String eventId);

        void showPart(List<ShowPartBean.PartBean> par);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
