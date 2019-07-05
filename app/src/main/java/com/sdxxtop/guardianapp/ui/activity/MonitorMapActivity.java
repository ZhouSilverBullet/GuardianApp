package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.DeviceMapBean;
import com.sdxxtop.guardianapp.presenter.MonitorMapPresenter;
import com.sdxxtop.guardianapp.presenter.contract.MonitorMapContract;
import com.sdxxtop.guardianapp.ui.adapter.MonitorMarkerAdapter;
import com.sdxxtop.guardianapp.utils.LocationUtil;
import com.sdxxtop.guardianapp.utils.MonitorMapMarkerUtil;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class MonitorMapActivity extends BaseMvpActivity<MonitorMapPresenter> implements MonitorMapContract.IView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_monitor_list)
    LinearLayout llMonitorList;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.tv_data_normal)
    TextView tvDataNormal;
    @BindView(R.id.tv_data_exception)
    TextView tvDataException;
    @BindView(R.id.tv_device_exception)
    TextView tvDeviceException;

    private AMap aMap;
    private MonitorMapMarkerUtil mapMarkerUtil;
    private MonitorMarkerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_monitor_map;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    protected void initView() {
        super.initView();
        topViewPadding(llContainor);
        setUpMap();
    }

    private void setUpMap() {
        if (mapView != null) {
            // 初始化地图
            aMap = mapView.getMap();
        }

        mAdapter = new MonitorMarkerAdapter(this);
        aMap.setInfoWindowAdapter(mAdapter);

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MarkerOptions options = marker.getOptions();
                String[] split = options.getSnippet().split(";");
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    if ("3".equals(split[0])) {
                        Intent intent = new Intent(MonitorMapActivity.this,DeviceDataDetailActivity.class);
                        intent.putExtra("deviceId", split[1]);
                        startActivity(intent);
                    } else {
                        marker.showInfoWindow();
                    }
                }
                return true;
            }
        });
        mapMarkerUtil = new MonitorMapMarkerUtil(this);
        // 定位
        initLocation();
    }

    @OnClick({R.id.ll_back, R.id.ll_monitor_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_monitor_list:

                break;
        }
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
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                } else {
                    String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                    Log.e("AmapErr", errText);
                }
            }
        });
    }

    @Override
    public void showMapInfo(DeviceMapBean bean) {
        if (bean != null) {
            DeviceMapBean.DeviceCount device_count = bean.getDevice_count();
            if (device_count != null) {
                tvDataNormal.setText("" + device_count.getNormal_count());
                tvDataException.setText("" + device_count.getExceed_count());
                tvDeviceException.setText("" + device_count.getAbnormal_count());
            } else {
                tvDataNormal.setText("--");
                tvDataException.setText("--");
                tvDeviceException.setText("--");
            }

            List<DeviceMapBean.DeviceInfo> data = bean.getDevice_info();
            if (data != null && data.size() > 0) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(new MyRunnable(data));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    class MyRunnable implements Runnable {
        private List<DeviceMapBean.DeviceInfo> mData;

        public MyRunnable(List<DeviceMapBean.DeviceInfo> mData) {
            this.mData = mData;
        }

        @Override
        public void run() {
            if (mData != null && mData.size() > 0) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (int i = 0; i < mData.size(); i++) {
                    Log.e("循环列表", "'" + mData.get(i).toString());
                    DeviceMapBean.DeviceInfo deviceInfo = mData.get(i);
                    if (deviceInfo.getStatus() == 3) {
                        aMap.addMarker(new MarkerOptions()
                                .position(mapMarkerUtil.getLatLng(deviceInfo.getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_point))
                                .title(deviceInfo.getDevice_name() + ";" + deviceInfo.getTpfpm() + ";" + deviceInfo.getTenpm())
                                .snippet("" + deviceInfo.getStatus() + ";" + deviceInfo.getDevice_id())
                                .anchor(0.5f, 0.5f));
                        aMap.addCircle(new CircleOptions().
                                center(mapMarkerUtil.getLatLng(deviceInfo.getLongitude())).
                                radius(1000).
                                fillColor(Color.parseColor("#33FF0000")).
                                strokeColor(Color.parseColor("#FF0000")).
                                strokeWidth(1));
                    } else if (deviceInfo.getStatus() == 2) {
                        aMap.addMarker(new MarkerOptions()
                                .position(mapMarkerUtil.getLatLng(deviceInfo.getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.orange_point))
                                .title(deviceInfo.getDevice_name() + ";" + deviceInfo.getTpfpm() + ";" + deviceInfo.getTenpm())
                                .snippet("" + deviceInfo.getStatus() + ";" + deviceInfo.getDevice_id())
                                .anchor(0.5f, 0.5f));
                        aMap.addCircle(new CircleOptions().
                                center(mapMarkerUtil.getLatLng(deviceInfo.getLongitude())).
                                radius(1000).
                                fillColor(Color.parseColor("#33FFA800")).
                                strokeColor(Color.parseColor("#FFA800")).
                                strokeWidth(1));
                    } else if (deviceInfo.getStatus() == 1) {
                        aMap.addMarker(new MarkerOptions()
                                .position(mapMarkerUtil.getLatLng(deviceInfo.getLongitude()))
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_point))
                                .title(deviceInfo.getDevice_name() + ";" + deviceInfo.getTpfpm() + ";" + deviceInfo.getTenpm())
                                .snippet("" + deviceInfo.getStatus() + ";" + deviceInfo.getDevice_id())
                                .anchor(0.5f, 0.5f));

                        aMap.addCircle(new CircleOptions().
                                center(mapMarkerUtil.getLatLng(deviceInfo.getLongitude())).
                                radius(1000).
                                fillColor(Color.parseColor("#3333B16D")).
                                strokeColor(Color.parseColor("#33B16D")).
                                strokeWidth(1));
                    }
                }

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapMarkerUtil.getLatLng(mData.get(1).getLongitude()), 12));
            }
        }
    }
}
