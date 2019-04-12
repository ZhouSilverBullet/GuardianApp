package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

/**
 * 用来copy使用的
 */
public interface FaceLivenessContract {
    interface IView extends BaseView {
        void faceSuccess(String address);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
