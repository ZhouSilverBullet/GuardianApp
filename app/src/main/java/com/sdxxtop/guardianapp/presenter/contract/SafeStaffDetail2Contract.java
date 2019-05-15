package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;

/**
 * 用来copy使用的
 */
public interface SafeStaffDetail2Contract {
    interface IView extends BaseView {

        void showData(EnterpriseUserdetailsBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
