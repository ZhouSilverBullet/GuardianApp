package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;

public interface GridMapContract {
    interface IView extends BaseView {
        void showMap(MainMapBean mainMapBean);

    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
