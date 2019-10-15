package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

public interface OrganizationListContract {
    interface IView extends BaseView {
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(int count, int type);
    }
}
