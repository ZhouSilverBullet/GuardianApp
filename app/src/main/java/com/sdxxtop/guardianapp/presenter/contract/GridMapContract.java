package com.sdxxtop.guardianapp.presenter.contract;

import com.amap.api.maps.model.LatLng;
import com.sdxxtop.guardianapp.base.BasePresenter;
import com.sdxxtop.guardianapp.base.BaseView;

import java.util.List;

public interface GridMapContract {
    interface IView extends BaseView {
        void showPolygon(List<LatLng> list);
    }

    interface IPresenter extends BasePresenter<IView> {

    }
}
