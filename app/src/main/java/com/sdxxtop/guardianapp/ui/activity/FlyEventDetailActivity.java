package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.FlyEventDetailBean;
import com.sdxxtop.guardianapp.presenter.FlyEventDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.VideoPlayAdapter;
import com.sdxxtop.guardianapp.ui.widget.MaxHeightRecyclerView;

import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.jzvd.JzvdStd;




public class FlyEventDetailActivity extends BaseMvpActivity<FlyEventDetailPresenter> implements FlyEventDetailContract.IView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_plane_name)
    TextView tvPlaneName;
    @BindView(R.id.tv_event_content)
    TextView tvEventContent;
    @BindView(R.id.tv_event_time)
    TextView tvEventTime;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.jzvideo)
    JzvdStd JZVideoPlayer;
    /********* 未上报状态隐藏 *********/
    @BindView(R.id.tv_temp_1)
    TextView tv_temp_1;
    @BindView(R.id.tv_temp_2)
    TextView tv_temp_2;
    @BindView(R.id.ll_video)
    LinearLayout ll_video;
    @BindView(R.id.recyclerView)
    MaxHeightRecyclerView recyclerView;
    @BindView(R.id.mapview)
    MapView mMapView;


    private VideoPlayAdapter adapter;
    private int eventId;
    private int type;
    private AMap aMap;

    @Override
    protected int getLayout() {
        return R.layout.activity_fly_event_detail;
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
        if (JZVideoPlayer != null) {
            JZVideoPlayer.releaseAllVideos();
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
    public void onBackPressedSupport() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra("type", 0);
        eventId = getIntent().getIntExtra("eventId", 0);
        initMap();
        if (type == 0) {
            tv_temp_1.setVisibility(View.GONE);
            tv_temp_2.setVisibility(View.GONE);
            ll_video.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            mMapView.setVisibility(View.GONE);
        } else {
            tv_temp_1.setVisibility(View.VISIBLE);
            tv_temp_2.setVisibility(View.VISIBLE);
            ll_video.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            mMapView.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new VideoPlayAdapter(R.layout.item_video_play, null);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = ((VideoPlayAdapter) adapter).getItem(position);
                JZVideoPlayer.setUp(item, " ");
                JZVideoPlayer.startVideo();
            }
        });
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
        }
        if (type == 1 && bean.uav_task != null) {
            String[] split = bean.uav_task.longitude.split(",");
            LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.grid_map_icon));
            markerOptions.position(latLng);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            aMap.addMarker(markerOptions);

            if (!TextUtils.isEmpty(bean.uav_video)) {
                String[] video = bean.uav_video.split(",");
                List<String> videoList = Arrays.asList(video);
                if (videoList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    JZVideoPlayer.setUp(videoList.get(0), " ");
                    Glide.with(this).load(videoList.get(0)).into(JZVideoPlayer.thumbImageView);
                    adapter.replaceData(videoList);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        }
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
