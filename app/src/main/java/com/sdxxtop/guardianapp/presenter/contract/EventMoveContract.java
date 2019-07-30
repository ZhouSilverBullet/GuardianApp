package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;

/**
 * 用来copy使用的
 */
public interface EventMoveContract {
    interface IView extends BaseView {

        void showData(EventShowBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
