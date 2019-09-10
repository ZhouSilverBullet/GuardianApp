package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.MainIndexBeanNew;
import com.sdxxtop.guardianapp.presenter.HomeFragmentPresenter;
import com.sdxxtop.guardianapp.presenter.contract.HomeFragmentContract;
import com.sdxxtop.guardianapp.ui.activity.CenterMessageActivity;
import com.sdxxtop.guardianapp.ui.activity.GridMapActivity;
import com.sdxxtop.guardianapp.ui.activity.HomeActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;
import com.sdxxtop.guardianapp.ui.adapter.HomeImageAdapter;
import com.sdxxtop.guardianapp.ui.adapter.MyHomeTabAdapter;
import com.sdxxtop.guardianapp.ui.widget.AutoTextView;
import com.sdxxtop.guardianapp.ui.widget.AutoTextViewManager;
import com.sdxxtop.guardianapp.ui.widget.UnScrolGridView;
import com.sdxxtop.guardianapp.ui.widget.imgservice.OnlineServiceActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link HomeFragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {


    @BindView(R.id.tv_part_name)
    TextView tvPartName;
    @BindView(R.id.iv_message_icon)
    ImageView ivMessageIcon;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.autotextview)
    AutoTextView autotextview;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.gridview)
    UnScrolGridView gridview;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    //    @BindView(R.id.tv_pm2)
//    TextView tvPm2;
//    @BindView(R.id.tv_pm1)
//    TextView tvPm1;
    @BindView(R.id.tv_current_wendu)
    ImageView tvCurrentWendu;
    @BindView(R.id.tv_wendu)
    TextView tvWendu;
    @BindView(R.id.tv_weather_status)
    TextView tvWeatherStatus;
    /*************** 权限 ***************/

    private boolean isAdmin;
    private HomeImageAdapter adapter;
    private MyHomeTabAdapter homeTabAdapter;
    private AutoTextViewManager manager;
    private MainIndexBeanNew mBean;

    public static HomeFragment newInstance(boolean isAdmin) {
        Bundle args = new Bundle();
        args.putBoolean("isAdmin", isAdmin);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);
        if (getArguments() != null) {
            isAdmin = getArguments().getBoolean("isAdmin");
        }

        llContainor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CenterMessageActivity.class);
                startActivity(intent);
            }
        });

        manager = new AutoTextViewManager(autotextview);
        homeTabAdapter = new MyHomeTabAdapter();
        gridview.setAdapter(homeTabAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        adapter = new HomeImageAdapter(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mPresenter.loadData();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public void showError(String error) {
//        mTextView.setText(error);
    }

    @Override
    public void showData(MainIndexBeanNew bean) {
        mBean = bean;
        Glide.with(getContext()).load(bean.temperature_img).into(tvCurrentWendu);
        setMessageCount(bean.pending_count);
//        tvPm1.setText("" + bean.avg_tpfpm);
//        tvPm2.setText("" + bean.avg_tenpm);
        tvWeatherStatus.setText(bean.air_quality);
        tvWendu.setText("" + bean.min_temperature + "℃/" + bean.max_temperature + "℃");
        ivMessageIcon.setImageResource(bean.unread_count == 0 ? R.drawable.message_normal : R.drawable.message_notice);

        List<String> list = new ArrayList<>();
        if (bean.pending_event != null) {
            for (MainIndexBeanNew.PendingEvent item : bean.pending_event) {
                list.add(item.title + "  截止日期: " + item.end_date);
            }
            manager.removeAutoTextRunnable();
            manager.setData(list);
            manager.start();
        }
        adapter.replaceData(bean.wheel_planting_video);
        homeTabAdapter.setLimits(bean, getActivity());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) getActivity()).hideLoadingDialog();
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            mPresenter.loadData();
        }
    }

    public void setMessageCount(int unRead) {
        if (unRead <= 0) {
            tvMsgCount.setVisibility(View.INVISIBLE);
        } else {
            tvMsgCount.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if (unRead < 10) {
                tvMsgCount.setBackgroundDrawable(getResources().getDrawable(R.drawable.point1));
            } else {
                if (unRead > 99) {
                    tvMsgCount.setBackgroundDrawable(getResources().getDrawable(R.drawable.point2));
                    unReadStr = "99+";
                }
            }
            if (unRead > 99) {
                unReadStr = "99+";
            }
            tvMsgCount.setText(unReadStr);
        }
    }

    @OnClick({R.id.ll_uva, R.id.rl_map, R.id.rl_online_service, R.id.rl_msg_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_uva:   // 无人机
                if (mBean != null && mBean.is_uav == 1) {
                    HomeActivity activity = ((HomeActivity) getActivity());
                    activity.setWurenjiClick();
                } else {
                    showToast("暂无权限");
                }
                break;
            case R.id.rl_map:  // 网格地图
                if (mBean != null && mBean.is_map == 1) {
                    startActivity(new Intent(getContext(), GridMapActivity.class));
                } else {
                    showToast("暂无权限");
                }
                break;
            case R.id.rl_online_service:
                Intent intent = new Intent(getContext(), OnlineServiceActivity.class);
                intent.putExtra("href", "https://tb.53kf.com/code/client/b722216fa3d928f41a494d544ac54dcb/2?device=android");
                startActivity(intent);
                break;
            case R.id.rl_msg_text:
                startActivity(new Intent(getContext(), TaskAgentsActivity.class));
                break;
        }
    }
}
