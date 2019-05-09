package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.GCRPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GCRContract;
import com.sdxxtop.guardianapp.ui.adapter.GridMarkerAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.MarkerImgLoad;
import com.sdxxtop.guardianapp.utils.MarkerSign;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.sdxxtop.guardianapp.utils.MarkerUtil.addSimulatedData;

public class GrantCompanyReportActivity extends BaseMvpActivity<GCRPresenter> implements GCRContract.IView, AMap.OnMapLoadedListener {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.map_view)
    MapView mMapView;


    private RxPermissions mRxPermissions;
    private AMap mAMap;
    private Marker tempMarker;
    private GridMarkerAdapter mAdapter;
    private MarkerImgLoad markerImgLoad;
    private final LatLng centerLocation = new LatLng(40.035613, 116.313903);

    @Override
    protected int getLayout() {
        return R.layout.activity_grant_company_report;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
//        showStatusBar();
    }
    
    @Override
    protected void initView() {
        super.initView();
        int reportType = getIntent().getIntExtra("reportType", -1);
        if (reportType==1){
            title.setTitleValue("网格员报告");
            title.getTvRight().setText("巡逻详情");
        }else if (reportType==2){
            title.setTitleValue("企业报告");
            title.getTvRight().setText("企业详情");
        }

        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initMap();
                }
            }
        });

    }

    private void initMap() {
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            mAMap.setOnMapLoadedListener(this);
            mAMap.setMinZoomLevel(8);
            mAMap.setMaxZoomLevel(20);
        }


        mAdapter = new GridMarkerAdapter(this);
        mAMap.setInfoWindowAdapter(mAdapter);

        markerImgLoad = new MarkerImgLoad(this);
        moveMapToPosition(centerLocation);
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                tempMarker = marker;
                if (marker.getObject().getClass().equals(MarkerSign.class)){
                    if (marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                    } else {
                        marker.showInfoWindow();
                    }
                }
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), mAMap.getCameraPosition().zoom));
                return true;
            }
        });
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (tempMarker != null && tempMarker.isInfoWindowShown()) {
                    tempMarker.hideInfoWindow();
                }
            }
        });
    }


    /**
     * by moos on 2018/01/12
     * func:移动地图视角到某个精确位置
     *
     * @param latLng 坐标
     */
    private void moveMapToPosition(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(
                        latLng,//新的中心点坐标
                        16,    //新的缩放级别
                        0,     //俯仰角0°~45°（垂直与地图时为0）
                        0      //偏航角 0~360° (正北方为0)
                ));
        mAMap.animateCamera(cameraUpdate, 300, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showError(String error) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mapView.onDestroy()，销毁地图
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 地图加载完成
     */
    @Override
    public void onMapLoaded() {
        //添加自定义Marker
        addCustomMarkersToMap();
    }

    /**
     * by moos on 2018/01/12
     * func:批量添加自定义marker到地图上
     */
    private void addCustomMarkersToMap() {
        List<LatLng> locations = new ArrayList<>();
        locations = addSimulatedData(centerLocation, 20, 0.02);
        for (int i = 0; i < locations.size(); i++) {
            markerImgLoad.addCustomMarker(locations.get(i), new MarkerSign(i), new MarkerImgLoad.OnMarkerListener() {
                @Override
                public void showMarkerIcon(MarkerOptions markerOptions, MarkerSign sign) {
                    Marker marker;
                    marker = mAMap.addMarker(markerOptions);
                    marker.setObject(sign);
                }
            });
        }
    }
}
