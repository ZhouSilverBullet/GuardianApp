package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.AllarticleBean;

public interface EventListContract {
    interface IView extends BaseView {
        void showData(AllarticleBean data);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(int count, int type);
    }
}
