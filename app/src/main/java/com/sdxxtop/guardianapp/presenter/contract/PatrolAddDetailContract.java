package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.PatrolReadBean;

/**
 * 用来copy使用的
 */
public interface PatrolAddDetailContract {
    interface IView extends BaseView {

        void showData(PatrolReadBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
