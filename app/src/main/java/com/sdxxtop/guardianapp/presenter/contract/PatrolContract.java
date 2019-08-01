package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.model.bean.TrackPointBean;

public interface PatrolContract {
    interface IView extends BaseView {
        void showData(SignLogBean signLogBean);

        void showTrackData(TrackPointBean bean);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
