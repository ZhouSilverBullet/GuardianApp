package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.RecordLogBean;

/**
 * 用来copy使用的
 */
public interface RecordLogContract {
    interface IView extends BaseView {

        void showData(RecordLogBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
