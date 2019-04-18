package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.ExamineFinishBean;
import com.sdxxtop.guardianapp.model.bean.StudyCheckBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;

public interface ExamineContract {
    interface IView extends BaseView {
        void showData(StudyQuestionBean studyQuestionBean);

        void pushQuestionSuccess(StudyCheckBean studyCheckBean);

        void finishSuccess(ExamineFinishBean finishBean);

        void finishFailure();
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
