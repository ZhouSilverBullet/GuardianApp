package com.sdxxtop.guardianapp.ui.fragment;


import android.os.Bundle;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.model.bean.DataEntry;
import com.sdxxtop.guardianapp.presenter.DataMonitoringPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DataMonitoringContract;
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
        if (bannerViewHolder != null) {
            bannerViewHolder.setPerMission(bean);
        }
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }

    public void setCurrentItem() {
        mMZBanner.getViewPager().setCurrentItem(1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            statusBar(true);
            mPresenter.loadData();
        }
    }
}
