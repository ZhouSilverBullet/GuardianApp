package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CourseListContract;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;

import javax.inject.Inject;

public class CourseListPresenter extends RxPresenter<CourseListContract.IView> implements CourseListContract.IPresenter {
    @Inject
    public CourseListPresenter() {
    }


}
