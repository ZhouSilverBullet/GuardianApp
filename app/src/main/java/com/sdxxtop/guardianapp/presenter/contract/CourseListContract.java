package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.StudyCourseBean;
import com.sdxxtop.guardianapp.model.bean.course.CourseCellBean;
import com.sdxxtop.guardianapp.model.bean.course.ExamCellBean;

import java.util.List;

public interface CourseListContract {
    interface IView extends BaseView {
        void showList(List<CourseCellBean> studyCourseBeans);
        void showExam(List<ExamCellBean> examCellBeans);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
