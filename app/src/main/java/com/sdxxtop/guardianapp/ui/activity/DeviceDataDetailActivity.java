package com.sdxxtop.guardianapp.ui.activity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;
import com.sdxxtop.guardianapp.presenter.DeviceDataDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DeviceDataDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.DeviceDataListAdapter;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomBarChartView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class DeviceDataDetailActivity extends BaseMvpActivity<DeviceDataDetailPresenter> implements DeviceDataDetailContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.cbcv_view)
    CustomBarChartView cbcv_view;

    private String deviceId;
    private DeviceDataListAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_device_data_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            deviceId = getIntent().getStringExtra("deviceId");
        }
        mPresenter.loadData(deviceId, "", "");
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeviceDataListAdapter(R.layout.item_device_data, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    mPresenter.loadListData(deviceId, "", "", adapter.getItemCount());
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    mPresenter.loadListData(deviceId, "", "", 0);
                }
            }
        });
        smartRefresh.autoRefresh();
    }

    @Override
    public void showData(DeviceDataBean bean) {
        List<DeviceDataBean.DustDataBean> data = bean.getDust_data();
        if (data != null && data.size() > 0) {
            cbcv_view.initData(data);
        }else{
            cbcv_view.setNoData();
        }
    }

    @Override
    public void showListData(DeviceDataBean bean, int pageSize) {
        if (smartRefresh != null) {
            smartRefresh.finishRefresh();
            smartRefresh.finishLoadMore();
        }
        List<DeviceDataBean.DustDataBean> data = bean.getDust_data();
        if (data != null&&data.size()>0) {
            if (pageSize == 0) {
                adapter.replaceData(bean.getDust_data());
            } else {
                adapter.addData(bean.getDust_data());
            }
        }
    }
}
