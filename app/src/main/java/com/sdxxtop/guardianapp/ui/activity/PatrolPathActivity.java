package com.sdxxtop.guardianapp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.presenter.PatrolPathPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.AMapUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;

import static com.sdxxtop.guardianapp.utils.MarkerImgLoad.convertViewToBitmap;

public class PatrolPathActivity extends BaseMvpActivity<PatrolPathPresenter> implements PatrolContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.map_view)
    MapView mMapView;

    private AMap aMap;
    private GeocodeSearch geocoderSearch;

    private List<LatLng> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_patrol_path;
    }

    @Override
    protected void initView() {
        super.initView();
        String id = getIntent().getStringExtra("id");
        int reportType = getIntent().getIntExtra("reportType",-1);
        title.setTitleValue(id + "巡逻报告");
        initMap();
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            geocoderSearch = new GeocodeSearch(this);
            setUpMap();
        }
    }


    private void setUpMap() {
        list = showListLat();
        //起点位置和  地图界面大小控制
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 7));
        aMap.setMapTextZIndex(2);
        // 绘制一条带有纹理的直线
        //手动数据测试
        //.add(new LatLng(26.57, 106.71),new LatLng(26.14,105.55),new LatLng(26.58, 104.82), new LatLng(30.67, 104.06))
        //添加到地图
        aMap.addPolyline( new PolylineOptions().addAll(list).color(Color.argb(255, 255, 20, 147)).width(7).setDottedLine(false).geodesic(false));

        LatLonPoint latLonPoint = new LatLonPoint(30.67, 104.06);

        //起点图标
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(latLonPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));

        //终点坐标
        LatLonPoint latLonPointEnd = new LatLonPoint(29.89, 107.7);
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(latLonPointEnd))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));

        addMarker();
    }

    private void addMarker() {
        for (int i = 1; i < list.size()-1; i++) {
            //起点图标
            aMap.addMarker(new MarkerOptions()
                    .position(list.get(i))
                    .anchor(0.5f,0.7f)  // icon偏移量
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_success)));
                    .icon(customizeMarkerIcon()));
        }
    }

    private BitmapDescriptor customizeMarkerIcon() {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.view_with_time, null);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        if (bitmapDescriptor!=null){
            return bitmapDescriptor;
        }else{
            return BitmapDescriptorFactory.fromResource(R.mipmap.ic_success);
        }
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void showData(SignLogBean signLogBean) {

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


    /***
     *经纬度集合
     */
    private List<LatLng> showListLat() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new LatLng(coords[i + 1], coords[i]));
        }
        return points;
    }

    private double[] coords = {
            104.06, 30.67,
            104.32, 30.88,
            104.94, 30.57,
            103.29, 30.2,
            103.81, 30.97,
            104.73, 31.48,
            106.06, 30.8,
            107.7, 29.89
    };
}
