package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;

/**
 * 用来copy使用的
 */
public interface GERContract {
    interface IView extends BaseView {

        void showIndexData(GERPIndexBean indexBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
