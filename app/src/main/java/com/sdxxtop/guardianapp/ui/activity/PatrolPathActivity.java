package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
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
import com.amap.api.services.geocoder.GeocodeSearch;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EnterpriseTrailBean;
import com.sdxxtop.guardianapp.presenter.PatrolPathPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PatrolPathContract;
import com.sdxxtop.guardianapp.ui.pop.StatSelectionDateWindow;
import com.sdxxtop.guardianapp.ui.widget.TabTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.Date2Util;
import com.sdxxtop.guardianapp.utils.DateUtil;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.sdxxtop.guardianapp.utils.MarkerImgLoad.convertViewToBitmap;

public class PatrolPathActivity extends BaseMvpActivity<PatrolPathPresenter> implements PatrolPathContract.IView {

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

    private GeocodeSearch geocoderSearch;
    private String chooseDay;

    private AMap aMap;
    private List<LatLng> list = new ArrayList<>();
    private String userid;
    private int reportType;  // 网格员:1  / 企业 :2
    private String name;
    private StatSelectionDateWindow selectionDateWindow;

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
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        ttv1.setValue("--", "巡逻总距离(km)");
        ttv2.setValue("--", "巡逻总时长(km)");
        if (aMap != null) {
            aMap.clear();
        }
        UIUtils.showToast(error);
    }

    @Override
    protected void initView() {
        super.initView();
        name = getIntent().getStringExtra("name");
        userid = getIntent().getStringExtra("userid");
        reportType = getIntent().getIntExtra("reportType", -1);
        title.setTitleValue(name + "巡逻报告");

        ttv1.setValue("--", "巡逻总距离(km)");
        ttv2.setValue("--", "巡逻总时长(km)");
        ttv2.tvLine.setVisibility(View.GONE);
        ttv1.tvLine.setVisibility(View.GONE);

        initMap();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.enterpriseTrail(userid, "", reportType);
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            geocoderSearch = new GeocodeSearch(this);
        }
    }


    /**
     * 画线
     *
     * @param data
     */
    private void setUpMap(List<EnterpriseTrailBean.TrailInfo> data) {
        //起点位置和  地图界面大小控制
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list.get(0), 7));
        aMap.setMapTextZIndex(2);
        // 绘制一条带有纹理的直线
        //手动数据测试
        //.add(new LatLng(26.57, 106.71),new LatLng(26.14,105.55),new LatLng(26.58, 104.82), new LatLng(30.67, 104.06))
        //添加到地图
        aMap.addPolyline(new PolylineOptions().addAll(list).color(getResources().getColor(R.color.green)).width(7).setDottedLine(false).geodesic(false));
        if (list.size() > 2) {
            addMarker(data);
        }

        LatLngBounds bounds = getLatLngBounds();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
    }

    /**
     * 绘制marker
     *
     * @param data
     */
    private void addMarker(List<EnterpriseTrailBean.TrailInfo> data) {
        for (int i = 1; i < list.size() - 1; i++) {
            //起点图标
            aMap.addMarker(new MarkerOptions()
                    .position(list.get(i))
                    .anchor(0.5f, 0.15f)  // icon偏移量
//                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_success)));
                    .icon(customizeMarkerIcon(data.get(i))));
        }

        //起点图标
        aMap.addMarker(new MarkerOptions()
                .position(list.get(0))
                .icon(customizeMarkerIconLastAndFirst(data.get(0),"起点")));

        //终点坐标
        if (list.size() > 0) {
            aMap.addMarker(new MarkerOptions()
                    .position(list.get(list.size() - 1))
                    .icon(customizeMarkerIconLastAndFirst(data.get(data.size()-1),"终点")));
        }
    }

    private BitmapDescriptor customizeMarkerIcon(EnterpriseTrailBean.TrailInfo trailInfo) {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.view_with_time, null);
        TextView time = markerView.findViewById(R.id.tv_time);
        String signTime = trailInfo.getSign_time();
        time.setText(DateUtil.getFormatMapTime(signTime));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        if (bitmapDescriptor != null) {
            return bitmapDescriptor;
        } else {
            return BitmapDescriptorFactory.fromResource(R.mipmap.ic_success);
        }
    }
    private BitmapDescriptor customizeMarkerIconLastAndFirst(EnterpriseTrailBean.TrailInfo trailInfo,String str) {
        final View markerView = LayoutInflater.from(mContext).inflate(R.layout.view_map_with_time, null);
        TextView time = markerView.findViewById(R.id.tv_time);
        TextView tv_title = markerView.findViewById(R.id.tv_title);
        String signTime = trailInfo.getSign_time();
        time.setText(DateUtil.getFormatMapTime(signTime));
        tv_title.setText(str);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        if (bitmapDescriptor != null) {
            return bitmapDescriptor;
        } else {
            return BitmapDescriptorFactory.fromResource(R.mipmap.ic_success);
        }
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
            case R.id.ttv_2:
                Intent intent = new Intent(this, SafeStaffDetailActivity.class);
                intent.putExtra("id", Integer.parseInt(userid));
                intent.putExtra("type", reportType);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.tv_end_time: // 选择时间
                showSelectDateWindow();
                break;
        }
    }

    @Override
    public void showData(EnterpriseTrailBean bean) {
        aMap.clear();
        list.clear();
        ttv1.setValue(String.valueOf(bean.getDistance()), "巡逻总距离(km)");
        ttv2.setValue(String.valueOf(bean.getTotal_time()), "巡逻总时长(km)");
        if (bean.getTrail_info() != null && bean.getTrail_info().size() > 0) {
            for (EnterpriseTrailBean.TrailInfo trailInfo : bean.getTrail_info()) {
                list.add(trailInfo.getLatLng());
            }
            setUpMap(bean.getTrail_info());
        }
    }

    /**
     * 选择日期的pop
     */
    private void showSelectDateWindow() {
        if (selectionDateWindow == null) {
            selectionDateWindow = new StatSelectionDateWindow(this, false, false, true);

            selectionDateWindow.setSelectorDateListener(new StatSelectionDateWindow.SelectorDateListener() {
                @Override
                public void onSelector(String date, CalendarDay calendarDay) {
                    // 请求网络
                    chooseDay = date;
                    tvEndTime.setText(getFormatDate(chooseDay));
                    mPresenter.enterpriseTrail(userid, date, reportType);
                    selectionDateWindow.dismiss();
                }
            });

            selectionDateWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }
        selectionDateWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_patrol_path, null), Gravity.BOTTOM, 0, 0);
    }

    public String getFormatDate(String sDate) {
        if (sDate.equals(Date2Util.getDate())) {
            return "今日";
        }
        String formatDate = "";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            formatDate = sdf2.format(sdf1.parse(sDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate;
    }
}
