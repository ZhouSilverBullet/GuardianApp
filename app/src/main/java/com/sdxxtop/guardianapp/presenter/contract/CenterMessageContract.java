package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;

/**
 * 用来copy使用的
 */
public interface CenterMessageContract {
    interface IView extends BaseView {

        void showData(UnreadIndexBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
