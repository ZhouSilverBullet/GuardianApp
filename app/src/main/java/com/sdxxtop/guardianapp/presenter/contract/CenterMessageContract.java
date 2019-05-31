package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;

import java.util.List;

/**
 * 用来copy使用的
 */
public interface CenterMessageContract {
    interface IView extends BaseView {

        void showData(List<UnreadIndexBean> bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
