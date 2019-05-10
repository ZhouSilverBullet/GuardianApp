package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SafeStaffDetailContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class SafeStaffDetailPresenter extends RxPresenter<SafeStaffDetailContract.IView> implements SafeStaffDetailContract.IPresenter {
    @Inject
    public SafeStaffDetailPresenter() {
    }


}
