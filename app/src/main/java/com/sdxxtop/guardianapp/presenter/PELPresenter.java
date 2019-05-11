package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;

import javax.inject.Inject;

/**
 * PEL:PartEventList简写
 */
public class PELPresenter extends RxPresenter<PELContract.IView> implements PELContract.IPresenter {
    @Inject
    public PELPresenter() {
    }


}
