package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;

import java.util.List;

public interface PatrolContract {
    interface IView extends BaseView {
        void showData(SignLogBean signLogBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
