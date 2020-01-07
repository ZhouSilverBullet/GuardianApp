package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.KqstDayBean;
import com.sdxxtop.guardianapp.model.bean.KqstMonthBean;

/**
 * 用来copy使用的
 *
 * KQST = 考勤视图
 */
public interface KQST_fmContract {
    interface IView extends BaseView {

        void showMonthData(KqstMonthBean bean);

        void showDayInfo(KqstDayBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
