package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.AutoLoginBean;

public interface SplashContract {
    interface IView extends BaseView {
        void autoSuccess(AutoLoginBean autoLoginBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
