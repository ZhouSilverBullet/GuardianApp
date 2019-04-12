package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;

public interface TaskAgentsContract {
    interface IView extends BaseView {
        void showData(int page, EventIndexBean eventIndexBean);
    }

    interface IPresenter extends BasePresenter<IView> {

        void loadData(int page, int type);
    }
}
