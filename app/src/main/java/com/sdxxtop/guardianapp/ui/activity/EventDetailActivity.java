package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;

import com.amap.api.maps.MapView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDetailContract;

import butterknife.BindView;

public class EventDetailActivity extends BaseMvpActivity<EventDetailPresenter> implements EventDetailContract.IView {

    @BindView(R.id.mapview)
    MapView mMapView;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }

}
