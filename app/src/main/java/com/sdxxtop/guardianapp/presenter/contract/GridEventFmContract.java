package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GridEventListBean;

/**
 * 用来copy使用的
 */
public interface GridEventFmContract {
    interface IView extends BaseView {

        void showData(GridEventListBean bean, int pathType, int start_page);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
