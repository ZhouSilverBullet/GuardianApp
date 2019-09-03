package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;
import com.sdxxtop.guardianapp.presenter.EventDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDetailContract;
import com.sdxxtop.guardianapp.ui.widget.fly_calendarview.MixtureChartView;

import butterknife.BindView;

public class EventDetailActivity extends BaseMvpActivity<EventDetailPresenter> implements EventDetailContract.IView {

    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.mixturechartview)
    MixtureChartView mixturechartview;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_plane_name)
    TextView tvPlaneName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_event_time)
    TextView tvEventTime;
    @BindView(R.id.tv_event_content)
    TextView tvEventContent;

    private int eventId;
    private AMap aMap;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
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
        initMap();
        eventId = getIntent().getIntExtra("eventId", 0);
    }

    @Override
    protected void initData() {
        mPresenter.loadData(eventId, "");
    }

    @Override
    public void showData(FlyEventDetailBean bean) {
        if (bean.uav_task != null) {
            tvTitle.setText("任务标题：" + bean.uav_task.title);
            tvPlaneName.setText("无人机名称 ：" + bean.uav_task.uav_name);
            tvEventContent.setText("任务内容：" + bean.uav_task.content);
            tvEventTime.setText("任务时间：" + bean.uav_task.add_date);
            tvName.setText("执行人 ：" + bean.uav_task.user_name);

            String[] split = bean.uav_task.longitude.split(",");
            LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.grid_map_icon));
            markerOptions.position(latLng);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            aMap.addMarker(markerOptions);
        }

        mixturechartview.setData();

    }

    private void initMap() {
        aMap = mMapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(false); //设置默认定位按钮是否显示，非必需设置。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }
}
