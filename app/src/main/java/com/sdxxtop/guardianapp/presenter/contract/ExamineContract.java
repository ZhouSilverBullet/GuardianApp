package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

public interface ExamineContract {
    interface IView extends BaseView {
        void showData(String data);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
