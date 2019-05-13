package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;

/**
 * PEL:PartEventList简写
 */
public interface PELContract {
    interface IView extends BaseView {

        void showData(PartEventListBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
