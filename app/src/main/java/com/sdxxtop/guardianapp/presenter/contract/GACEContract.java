package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EnterpriseCompanyBean;

/**
 *  GridAndCompanyEventContract
 */
public interface GACEContract {
    interface IView extends BaseView {

        void showData(EnterpriseCompanyBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
