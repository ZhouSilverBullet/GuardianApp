package com.sdxxtop.guardianapp.presenter;

import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ContactContract;

import javax.inject.Inject;

public class ContactPresenter extends RxPresenter<ContactContract.IView> implements ContactContract.IPresenter {
    @Inject
    public ContactPresenter() {
    }


}
