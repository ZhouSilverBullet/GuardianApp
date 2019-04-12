package com.sdxxtop.guardianapp.presenter.contract;

import com.amap.api.maps.model.LatLng;
import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;
import com.sdxxtop.guardianapp.model.bean.MainMapBean;

import java.util.List;

public interface GridMapContract {
    interface IView extends BaseView {
        void showPolygon(MainMapBean.UserBean middle, List<LatLng> list);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
