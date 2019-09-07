package com.sdxxtop.guardianapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link DataMonitoringFragment} subclass.
 */
public class DataMonitoringFragment extends BaseMvpFragment<DataMonitoringPresenter> implements DataMonitoringContract.IView {

    @BindView(R.id.banner)
    MZBannerView mMZBanner;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.text_1)
    TextView text_1;
    @BindView(R.id.text_2)
    TextView text_2;
    @BindView(R.id.text_3)
    TextView text_3;

    private int mCurrentItem = 0;
    private AuthDataBean mBean;  // 权限

    private static int[] RES = {R.drawable.yangchenjiance, R.drawable.wurenji_img};
    private static String[] TITLE = {"扬尘监测", "无人机"};

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
        // 设置数据
        mMZBanner.setIndicatorVisible(false);
        mMZBanner.setPages(mockData(), new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<DataEntry> mockData() {
        List<DataEntry> list = new ArrayList<>();
        DataEntry dataEntry = null;
        for (int i = 0; i < RES.length; i++) {
            dataEntry = new DataEntry();
            dataEntry.resId = RES[i];
            dataEntry.title = TITLE[i];
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
        if (isVisible()){
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


    public void setCurrentItem(int index) {
        switch (index) {
            case 0:
                tvDesc.setText(getResources().getString(R.string.yc_tx));
                text_1.setText("地图");
                text_2.setText("列表");
                text_3.setText("报表");
                break;
            case 1:
                tvDesc.setText(getResources().getString(R.string.wurenji_tx));
                text_1.setText("热力图");
                text_2.setText("数据");
                text_3.setText("统计");
                break;
        }
    }

    public void setCurrentItem() {
        mMZBanner.getViewPager().setCurrentItem(1);
        setCurrentItem(1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }

    @OnClick({R.id.ll_layout_1, R.id.ll_layout_2, R.id.ll_layout_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_layout_1:
                if (mCurrentItem == 0) {//扬尘
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
                if (mCurrentItem == 0) {//扬尘
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
