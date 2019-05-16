package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;

/**
 * GACP:GridAndCompanyPatrol
 */
public interface GACPContract {
    interface IView extends BaseView {

        void showData(GridreportPatrolBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
