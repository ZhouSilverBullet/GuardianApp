package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.FlyEventDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.VideoPlayAdapter;
import com.sdxxtop.guardianapp.ui.widget.MaxHeightRecyclerView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.jzvd.JzvdStd;

public class FlyEventDetailActivity extends BaseMvpActivity<FlyEventDetailPresenter> implements FlyEventDetailContract.IView {

    @BindView(R.id.recyclerView)
    MaxHeightRecyclerView recyclerView;
    @BindView(R.id.mapview)
    MapView mMapView;
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

    private VideoPlayAdapter adapter;

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
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(""+i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new VideoPlayAdapter(R.layout.item_video_play, list);
        recyclerView.setAdapter(adapter);

        JZVideoPlayer.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , " ");
        Glide.with(this).load("https://i0.hdslb.com/bfs/archive/19252a5dac6a250c6cabb3b9c3b005f734718e60.jpg@880w_440h.jpg").into(JZVideoPlayer.thumbImageView);
    }
}
