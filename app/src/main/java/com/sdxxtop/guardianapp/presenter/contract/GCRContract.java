package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;

/**
 * GrantCompanyReportContract
 */
public interface GCRContract {
    interface IView extends BaseView {

        void showData(EnterpriseIndexBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
