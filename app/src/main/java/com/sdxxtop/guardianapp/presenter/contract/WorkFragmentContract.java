package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;

/**
 * 用来copy使用的
 */
public interface WorkFragmentContract {
    interface IView extends BaseView {

        void showIndex(WorkIndexBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
