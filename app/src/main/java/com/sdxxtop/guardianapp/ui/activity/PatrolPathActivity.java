package com.sdxxtop.guardianapp.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.SignLogBean;
import com.sdxxtop.guardianapp.presenter.PatrolPathPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;
import com.sdxxtop.guardianapp.ui.widget.TabTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.AMapUtil;
import com.sdxxtop.guardianapp.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.sdxxtop.guardianapp.utils.MarkerImgLoad.convertViewToBitmap;

public class PatrolPathActivity extends BaseMvpActivity<PatrolPathPresenter> implements PatrolContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.ttv_1)
    TabTextView ttv1;
    @BindView(R.id.ttv_2)
    TabTextView ttv2;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private TimePickerView pvTime;  // 时间选择控件
    private GeocodeSearch geocoderSearch;

    private AMap aMap;
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
        int reportType = getIntent().getIntExtra("reportType", -1);
        title.setTitleValue(id + "巡逻报告");

        ttv1.setValue("789789", "巡逻总距离(km)");
        ttv2.setValue("789789", "巡逻总时长(km)");
        ttv2.tvLine.setVisibility(View.GONE);
        ttv1.tvLine.setVisibility(View.GONE);

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
        aMap.addPolyline(new PolylineOptions().addAll(list).color(getResources().getColor(R.color.green)).width(7).setDottedLine(false).geodesic(false));

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
        LatLngBounds bounds = getLatLngBounds();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
    }

    private void addMarker() {
        for (int i = 1; i < list.size() - 1; i++) {
            //起点图标
            aMap.addMarker(new MarkerOptions()
                    .position(list.get(i))
                    .anchor(0.5f, 0.15f)  // icon偏移量
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_success)));
                    .icon(customizeMarkerIcon()));
        }
    }

    private BitmapDescriptor customizeMarkerIcon() {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.view_with_time, null);
        TextView time = markerView.findViewById(R.id.tv_time);
//        time.setVisibility(View.GONE);
        time.setText("2019/04/03 \n" + "12:08:12");
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        if (bitmapDescriptor != null) {
            return bitmapDescriptor;
        } else {
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

    private LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < list.size(); i++) {
            b.include(list.get(i));
        }
        return b.build();
    }

    @OnClick({R.id.ttv_1, R.id.ttv_2, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ttv_1:
                break;
            case R.id.ttv_2:
                break;
            case R.id.tv_end_time: // 选择时间
                if (pvTime == null) {
                    initLeftTimePicker();
                    pvTime.show();
                } else {
                    pvTime.show();
                }
                break;
        }
    }


    /**
     * 初始化时间选择控件
     */
    private void initLeftTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvEndTime.setText(DateUtil.getTime(date));
                tvEndTime.setTextColor(getResources().getColor(R.color.black));
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("pvLeftTime", "onTimeSelectChanged");
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }
}
