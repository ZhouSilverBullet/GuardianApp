package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.UcenterIndexBean;

/**
 * 用来copy使用的
 */
public interface MineContract {
    interface IView extends BaseView {
        void showList(UcenterIndexBean ucenterIndexBean);

        void changeIconSuccess();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
