package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.StudyCourseBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseCellBean;
import com.sdxxtop.guardianapp.model.bean.course.ExamCellBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.CourseListContract;
import com.sdxxtop.guardianapp.presenter.contract.HomeContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CourseListPresenter extends RxPresenter<CourseListContract.IView> implements CourseListContract.IPresenter {
    @Inject
    public CourseListPresenter() {
    }

    public void loadData(String name) {
        Params params = new Params();
        Observable<RequestBean<StudyCourseBean>> observable = getEnvirApi().postStudyCourse(name, params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<StudyCourseBean>() {
            @Override
            public void onSuccess(StudyCourseBean studyCourseBean) {

                if ("exam".equals(name)) {
                    List<ExamCellBean> exam = studyCourseBean.getExam();
                    if (exam != null) {
                        mView.showExam(exam);
                    }
                } else {
                    List<CourseCellBean> course = studyCourseBean.getCourse();
                    if (course != null) {
                        mView.showList(course);
                    }
                }

            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });
        addSubscribe(disposable);
    }
}
