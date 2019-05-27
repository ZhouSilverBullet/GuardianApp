package com.sdxxtop.guardianapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
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
import com.sdxxtop.guardianapp.model.bean.GridreportIndexBean;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.GGRPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GGRContract;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomEventLayout;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.LocationUtil;
import com.sdxxtop.guardianapp.utils.MarkerImgLoad;
import com.sdxxtop.guardianapp.utils.MarkerSign;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class GrantGridReportActivity extends BaseMvpActivity<GGRPresenter> implements GGRContract.IView, AMap.OnMapLoadedListener,
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
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;


    private RxPermissions mRxPermissions;
    private AMap mAMap;
    private MarkerImgLoad markerImgLoad;

    private int part_typeid = 0;  // 区域选择默认值
    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowData = new ArrayList<>();
    private boolean isMapLoadSuccess, isMapDataLoadSuccess;   // 地图加载完成标识/地图数据加载完成标识
    private List<GridreportIndexBean.GridNowInfo> userInfos;

    @Override
    protected int getLayout() {
        return R.layout.activity_grant_grid_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        UIUtils.showToast(error);
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
        mPresenter.gridreportIndex(part_typeid);
    }

    @Override
    protected void initView() {
        super.initView();
        title.setTitleValue("网格员报告");
        title.getTvRight().setText("巡逻详情");

        title.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrantGridReportActivity.this, GACPatrolDetailActivity.class);  // 轨迹详情
                startActivity(intent);
            }
        });
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initMap();
                    initLocation();
                }
            }
        });

        addTabView(null);

    }

    /**
     * 定位
     */
    private void initLocation() {
        LocationUtil locationUtil = new LocationUtil();
        locationUtil.startLocate(this);
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation amapLocation) {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    locationUtil.stopLocation();
                    LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                } else {
                    String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                    Log.e("AmapErr", errText);
                }
            }
        });
    }

    //添加详情标签
    private void addTabView(GridreportIndexBean bean) {
        if (bean == null)
            return;
        List<TabTextBean> list = new ArrayList<>();
        list.add(new TabTextBean(1, String.valueOf(bean.getGrid_count()), "总人数"));
        list.add(new TabTextBean(2, String.valueOf(bean.getGrid_distance()), "巡逻总距离(km)"));
        list.add(new TabTextBean(3, String.valueOf(bean.getGrid_sign_time()), "巡逻总时长(h)"));

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

//        mAdapter = new GridMarkerAdapter(this);
//        mAMap.setInfoWindowAdapter(mAdapter);

        markerImgLoad = new MarkerImgLoad(this);

        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MarkerOptions options = marker.getOptions();
                Intent intent = new Intent(mContext, PatrolPathActivity.class);
                intent.putExtra("name", options.getSnippet());
                intent.putExtra("reportType", 1);
                intent.putExtra("userid", options.getTitle());
                mContext.startActivity(intent);
                return true;
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
    private void addCustomMarkersToMap(List<GridreportIndexBean.GridNowInfo> data) {
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
//            mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 30));
            moveMapToPosition(markerImgLoad.getLatLng(data.get(0).getLongitude()));
        }
    }


    @OnClick(R.id.rl_area_layout)
    public void onViewClicked() {
        initPopWindows();
    }

    private void initPopWindows() {
        AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GrantGridReportActivity.this, llContainorTemp, popWondowData, tvArea, tvBg);
        popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int partTypeid, String partName) {
                part_typeid = partTypeid;
                mPresenter.gridreportIndex(part_typeid);
            }
        });
    }

    @Override
    public void onTabClick(int num) {
//        Intent intent = new Intent(GrantGridReportActivity.this, GACPatrolDetailActivity.class);  // 轨迹详情
//        startActivity(intent);
    }

    @Override
    public void showData(GridreportIndexBean bean) {
        mAMap.clear();
        tvNowCount.setText("（在线人数" + bean.getGrid_now_count() + "人）");
        tvArea.setText(bean.getEvent_name());
        addTabView(bean);
        popWondowData.clear();
        if (bean.getPart() != null && bean.getPart().size() > 0) {
            for (GridreportIndexBean.GridPartBean gridPartBean : bean.getPart()) {
                popWondowData.add(new AreaSelectPopWindow.PopWindowDataBean(gridPartBean.getPart_id(), gridPartBean.getPart_name()));
            }
        }
        if (bean.getGrid_now_info() != null && bean.getGrid_now_info().size() > 0) {
            isMapDataLoadSuccess = true;
            userInfos = bean.getGrid_now_info();
            addCustomMarkersToMap(userInfos);
        }
    }
}
