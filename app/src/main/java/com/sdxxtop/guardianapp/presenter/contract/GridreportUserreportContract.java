package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;

/**
 * 用来copy使用的
 */
public interface GridreportUserreportContract {
    interface IView extends BaseView {

        void showData(GridreportUserreportBean bean,int start_page);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
