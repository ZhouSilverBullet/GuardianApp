package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.ArticleIndexBean;
import com.sdxxtop.guardianapp.model.bean.InitBean;

public interface HomeContract {
    interface IView extends BaseView {
        void showInit(InitBean initBean);

        void showPermission(ArticleIndexBean bean,int position,boolean wasSelected);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
