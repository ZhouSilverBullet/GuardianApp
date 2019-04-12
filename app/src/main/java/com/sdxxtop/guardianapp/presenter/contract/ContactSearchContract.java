package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.ContactIndexBean;

import java.util.List;

/**
 * 用来copy使用的
 */
public interface ContactSearchContract {
    interface IView extends BaseView {
        void showList(List<ContactIndexBean.ContactBean> contactBean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
