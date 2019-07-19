package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;

public interface ContactContract {
    interface IView extends BaseView {
        void showList(ContactIndexBean contactIndexBean);

        void showSearchList(ContactIndexBean contactIndexBean);
    }

    interface IPresenter extends BasePresenter<ContactContract.IView> {

    }
}
