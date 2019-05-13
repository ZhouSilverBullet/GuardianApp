package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventListBean;

/**
 * 事件统计
 */
public interface EventStatistyContract {
    interface IView extends BaseView {

        void showListData(EventListBean listBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
