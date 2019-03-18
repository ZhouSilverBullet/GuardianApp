package com.xuxin.guardianapp.presenter.contract;

import com.xuxin.guardianapp.base.BasePresenter;
import com.xuxin.guardianapp.base.BaseView;
import com.xuxin.guardianapp.model.bean.LearnNewsBean;

import java.util.List;

public interface NewsListFragmentContract {
    interface IView extends BaseView {
        void showData(List<LearnNewsBean> data);
    }

    interface IPresenter extends BasePresenter<IView> {
        void loadData(int count,int type);
    }
}
