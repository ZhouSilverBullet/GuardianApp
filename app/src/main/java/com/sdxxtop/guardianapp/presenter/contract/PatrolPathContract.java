package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EnterpriseTrailBean;

/**
 * 用来copy使用的
 */
public interface PatrolPathContract {
    interface IView extends BaseView {

        void showData(EnterpriseTrailBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
