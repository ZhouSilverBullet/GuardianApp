package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;

/**
 * 用来copy使用的
 */
public interface SafeStaffDetailContract {
    interface IView extends BaseView {

        void showData(EnterpriseSecurityBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
