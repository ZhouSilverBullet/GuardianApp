package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;

/**
 * 用来copy使用的
 */
public interface EventDiscretionListContract {
    interface IView extends BaseView {

        void showData(int size, EventDiscretionListBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
