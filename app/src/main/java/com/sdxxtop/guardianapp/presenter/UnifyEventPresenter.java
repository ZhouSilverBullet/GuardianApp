package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.UnifyEventContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class UnifyEventPresenter extends RxPresenter<UnifyEventContract.IView> implements UnifyEventContract.IPresenter {
    @Inject
    public UnifyEventPresenter() {
    }


}
