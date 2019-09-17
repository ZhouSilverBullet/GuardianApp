package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GridEventCountBean;

/**
 * 用来copy使用的
 */
public interface GridEventContract {
    interface IView extends BaseView {

        void showData(GridEventCountBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
