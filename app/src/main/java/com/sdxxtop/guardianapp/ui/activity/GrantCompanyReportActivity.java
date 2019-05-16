package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EnterpriseIndexBean;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.GCRPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GCRContract;
import com.sdxxtop.guardianapp.ui.adapter.GridMarkerAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomEventLayout;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.MarkerImgLoad;
import com.sdxxtop.guardianapp.utils.MarkerSign;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class GrantCompanyReportActivity extends BaseMvpActivity<GCRPresenter> implements GCRContract.IView, AMap.OnMapLoadedListener,
        CustomEventLayout.OnTabClickListener {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.cel_view)
    CustomEventLayout celView;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.tv_now_count)
    TextView tvNowCount;
    @BindView(R.id.ll_area_layout)
    LinearLayout llAreaLayout;
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;


    private RxPermissions mRxPermissions;
    private AMap mAMap;
    private Marker tempMarker;
    private GridMarkerAdapter mAdapter;
    private MarkerImgLoad markerImgLoad;

    private int part_typeid = 0;  // 区域选择默认值
    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowData = new ArrayList<>();
    private boolean isMapLoadSuccess, isMapDataLoadSuccess;   // 地图加载完成标识/地图数据加载完成标识
    private List<EnterpriseIndexBean.UserInfo> userInfos;

    @Override
    protected int getLayout() {
        return R.layout.activity_grant_company_report;
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
//        showStatusBar();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.enterpriseIndex(part_typeid);
    }

    @Override
    protected void initView() {
        super.initView();
        title.setTitleValue("企业报告");
        title.getTvRight().setText("企业详情");
        title.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrantCompanyReportActivity.this, GACEventDetailActivity.class);   // 企业详情
                startActivity(intent);
            }
        });
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

    //添加详情标签
    private void addTabView(EnterpriseIndexBean bean) {
        if (bean == null)
            return;
        List<TabTextBean> list = new ArrayList<>();
        list.add(new TabTextBean(1, String.valueOf(bean.getPart_count()), "企业数"));
        list.add(new TabTextBean(2, String.valueOf(bean.getUser_count()), "安全管理员数"));
        list.add(new TabTextBean(3, String.valueOf(bean.getTrai_count()), "考试培训次数"));
        list.add(new TabTextBean(4, String.valueOf(bean.getReport_info()), "上报自查次数"));
        celView.addLayout(list);
        celView.setOnTabClickListener(this);
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
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                tempMarker = marker;
                if (marker.getObject().getClass().equals(MarkerSign.class)) {
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
                        13,    //新的缩放级别
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
        isMapLoadSuccess = true;
        //添加自定义Marker
        addCustomMarkersToMap(userInfos);
    }

    /**
     * by moos on 2018/01/12
     * func:批量添加自定义marker到地图上
     */
    private void addCustomMarkersToMap(List<EnterpriseIndexBean.UserInfo> data) {
        if (data == null)
            return;
        if (isMapLoadSuccess && isMapDataLoadSuccess) {
            LatLngBounds.Builder builder = LatLngBounds.builder();
            for (int i = 0; i < data.size(); i++) {
                Log.e("循环列表", "'" + data.get(i).toString());
                markerImgLoad.addCustomMarker(data.get(i), new MarkerSign(i), new MarkerImgLoad.OnMarkerListener() {
                    @Override
                    public void showMarkerIcon(MarkerOptions markerOptions, MarkerSign sign) {
                        Marker marker;
                        marker = mAMap.addMarker(markerOptions);
                        marker.setObject(sign);
                    }
                });
//                builder.include(markerImgLoad.getLatLng(data.get(i).getLongitude()));
            }
            moveMapToPosition(markerImgLoad.getLatLng(data.get(0).getLongitude()));
        }
    }


    @OnClick(R.id.ll_area_layout)
    public void onViewClicked() {
        initPopWindows();
    }

    private void initPopWindows() {
        AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GrantCompanyReportActivity.this, llContainorTemp, popWondowData, tvArea, tvBg);
        popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int partTypeid, String partName) {
                part_typeid = partTypeid;
                mPresenter.enterpriseIndex(part_typeid);
            }
        });
    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(GrantCompanyReportActivity.this, GACEventDetailActivity.class);   // 企业详情
        startActivity(intent);
    }

    @Override
    public void showData(EnterpriseIndexBean bean) {
        mAMap.clear();
        tvNowCount.setText("（在线人数" + bean.getNow_count() + "人）");
        tvArea.setText(bean.getEvent_name());
        addTabView(bean);
        popWondowData.clear();
        if (bean.getPart_info() != null && bean.getPart_info().size() > 0) {
            for (EnterpriseIndexBean.PartInfo partInfo : bean.getPart_info()) {
                popWondowData.add(new AreaSelectPopWindow.PopWindowDataBean(partInfo.getPart_id(), partInfo.getPart_name()));
            }
        }
        if (bean.getUser_info() != null && bean.getUser_info().size() > 0) {
            isMapDataLoadSuccess = true;
            userInfos = bean.getUser_info();
            addCustomMarkersToMap(userInfos);
        }
    }
}
