package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.model.bean.DataEntry;
import com.sdxxtop.guardianapp.presenter.DataMonitoringPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DataMonitoringContract;
import com.sdxxtop.guardianapp.ui.activity.DeviceListActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyDataListActivity;
import com.sdxxtop.guardianapp.ui.activity.MonitorMapActivity;
import com.sdxxtop.guardianapp.ui.adapter.BannerViewHolder;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.MZBannerView;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link DataMonitoringFragment} subclass.
 */
public class DataMonitoringFragment extends BaseMvpFragment<DataMonitoringPresenter> implements DataMonitoringContract.IView {

    @BindView(R.id.banner)
    MZBannerView mMZBanner;

    private static int[] RES = {R.drawable.yangchenjiance, R.drawable.wurenji_img};
    private static String[] TITLE = {"扬尘监测", "无人机"};
    private BannerViewHolder bannerViewHolder;
    private AuthDataBean mBean;
    private int currentSelectIndex = 0;  //选中的item

    public static DataMonitoringFragment newInstance() {
        Bundle args = new Bundle();
        DataMonitoringFragment fragment = new DataMonitoringFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_data_monitor;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        statusBar(true);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new DataMonitoringAdapter(R.layout.item_monitor_list, null);
//        recyclerView.setAdapter(adapter);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < RES.length; i++) {
            list.add(RES[i]);
        }

        bannerViewHolder = new BannerViewHolder();
        // 设置数据
        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setPages(mockData(), new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return bannerViewHolder;
            }
        });

        bannerViewHolder.setOnTabClick(new BannerViewHolder.OnTabClickListener() {
            @Override
            public void onTabClick(int index, int layoutId) {
                onClick(index, layoutId);
            }
        });

        mMZBanner.getViewPager().setCurrentItem(currentSelectIndex);
    }

    private List<DataEntry> mockData() {
        List<DataEntry> list = new ArrayList<>();
        DataEntry dataEntry = null;
        for (int i = 0; i < RES.length; i++) {
            dataEntry = new DataEntry();
            dataEntry.resId = RES[i];
            dataEntry.title = TITLE[i];
            dataEntry.type = i;
            list.add(dataEntry);
        }
        return list;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mPresenter.loadData();
        }
    }

    @Override
    public void showData(AuthDataBean bean) {
        mBean = bean;
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    public void setCurrentItem() {
        if (mMZBanner!=null){
            mMZBanner.getViewPager().setCurrentItem(1);
        }else{
            currentSelectIndex = 1;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    public void onClick(int index, int layoutId) {
        if (mBean == null) {
            return;
        }
        switch (layoutId) {
            case R.id.ll_layout_1:
                if (index == 0) {//扬尘
                    if (mBean.is_raise_dust == 1) {
                        startActivity(new Intent(getContext(), MonitorMapActivity.class));
                    } else {
                        showToast("暂无权限");
                    }
                } else {
                    showToast("暂未开放");
                }
                break;
            case R.id.ll_layout_2:
                if (index == 0) {//扬尘
                    if (mBean.is_raise_dust == 1) {
                        Intent intent = new Intent(getContext(), DeviceListActivity.class);
                        intent.putExtra("status", "全部");
                        startActivity(intent);
                    } else {
                        showToast("暂无权限");
                    }
                } else {
                    if (mBean.is_uav == 1) {
                        startActivity(new Intent(getContext(), FlyDataListActivity.class));
                    } else {
                        showToast("暂无权限");
                    }
                }
                break;
            case R.id.ll_layout_3:
                showToast("暂未开放");
                break;
        }
    }

}
