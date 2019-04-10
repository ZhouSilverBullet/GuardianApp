package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.LoginBean;

public interface LoginContract {
    interface IView extends BaseView {
        void loginSuccess(LoginBean loginBean);
    }

    interface IPresenter extends BasePresenter<LoginContract.IView> {
        void login(String mobile, String authCode, String partId);
    }
}
