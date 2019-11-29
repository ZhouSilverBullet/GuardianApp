package com.sdxxtop.guardianapp.presenter;


import com.sdxxtop.guardianapp.base.RxPresenter;
import com.sdxxtop.guardianapp.model.bean.ExamineFinishBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.StudyQuestionBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.contract.ExamineContract;
import com.sdxxtop.guardianapp.utils.UIUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ExaminePresenter extends RxPresenter<ExamineContract.IView> implements ExamineContract.IPresenter {
    @Inject
    public ExaminePresenter() {
    }

    public void loadData(int examId, int number, String attendId) {
        Params params = new Params();
        params.put("ei", examId);
        params.put("nm", number);
        params.put("ai", attendId);
        Observable<RequestBean<StudyQuestionBean>> observable = getEnvirApi().postStudyQuestion(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<StudyQuestionBean>() {
            @Override
            public void onSuccess(StudyQuestionBean studyQuestionBean) {
                if (mView != null) {
                    mView.showData(studyQuestionBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }

    public void pushSelectQuestion(int examId, int questionId, String answer, int number, String attendId) {

        Params params = new Params();
        params.put("ei", examId);
        params.put("qi", questionId);
        params.put("nm", number);
        params.put("an", answer);
        params.put("ai", attendId);

        Observable<RequestBean> observable = getEnvirApi().postStudyCheck(params.getData());
        Disposable disposable = RxUtils.handleHttp(observable, new IRequestCallback<RequestBean>() {
            @Override
            public void onSuccess(RequestBean checkBean) {
                if (mView != null) {
                    mView.pushQuestionSuccess(null);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                UIUtils.showToast(error);
            }
        });

        addSubscribe(disposable);
    }

    public void finishData(int examId, String attendId) {
        Params params = new Params();
        params.put("ei", examId);
        params.put("ai", attendId);
        Observable<RequestBean<ExamineFinishBean>> observable = getEnvirApi().postStudyFinish(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<ExamineFinishBean>() {
            @Override
            public void onSuccess(ExamineFinishBean finishBean) {
                if (mView != null) {
                    mView.finishSuccess(finishBean);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mView != null) {
                    mView.finishFailure();
                    UIUtils.showToast(error);
                }
            }
        });

        addSubscribe(disposable);
    }
}
