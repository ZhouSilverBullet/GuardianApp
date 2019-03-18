package com.sdxxtop.guardianapp.presenter.contract;

import com.sdxxtop.guardianapp.model.bean.LearnNewsBean;
import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

import java.util.List;

public interface NewsListFragmentContract {
    interface IView extends BaseView {
        void showData(List<LearnNewsBean> data);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(int count,int type);
    }
}
