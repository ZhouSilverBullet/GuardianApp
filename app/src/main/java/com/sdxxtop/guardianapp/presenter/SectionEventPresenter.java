package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.SectionEventContract;

import javax.inject.Inject;

/**
 * 用来copy使用的
 */
public class SectionEventPresenter extends RxPresenter<SectionEventContract.IView> implements SectionEventContract.IPresenter {
    @Inject
    public SectionEventPresenter() {
    }


}
