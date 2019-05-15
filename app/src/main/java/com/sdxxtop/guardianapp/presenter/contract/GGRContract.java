package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GridreportIndexBean;

/**
 * 用来copy使用的
 */
public interface GGRContract {
    interface IView extends BaseView {
        void showData(GridreportIndexBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
