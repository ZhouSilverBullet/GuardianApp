package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.RequestBean;

/**
 * 用来copy使用的
 */
public interface ReCheckContract {
    interface IView extends BaseView {

        void showMsg(RequestBean bean);

    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
