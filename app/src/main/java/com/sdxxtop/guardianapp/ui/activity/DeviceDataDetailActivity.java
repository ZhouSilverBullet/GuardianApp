package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;
import com.sdxxtop.guardianapp.presenter.DeviceDataDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DeviceDataDetailContract;
import com.sdxxtop.guardianapp.ui.adapter.DeviceDataListAdapter;
import com.sdxxtop.guardianapp.ui.widget.DeviceDetailTimeSelect;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomBarChartView;
import com.sdxxtop.guardianapp.utils.Date2Util;

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
    @BindView(R.id.ddts_view)
    DeviceDetailTimeSelect ddts_view;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private String deviceId;
    private DeviceDataListAdapter adapter;
    private String day = Date2Util.getToday();
    private String time = "00:00 - 23:00";

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
        mPresenter.loadData(deviceId, day, time);
        mPresenter.loadListData(deviceId, day, time, 0);
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
                    mPresenter.loadListData(deviceId, day, time, adapter.getItemCount());
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    mPresenter.loadListData(deviceId, day, time, 0);
                }
            }
        });

        ddts_view.setOnDateSelect(new DeviceDetailTimeSelect.OnDateSelect() {
            @Override
            public void onSelect(String selectDay, String selectTime) {
                day = selectDay;
                time = selectTime;
                mPresenter.loadData(deviceId, day, time);
                mPresenter.loadListData(deviceId, day, time, 0);
            }
        });
    }

    @Override
    public void showData(DeviceDataBean bean) {
        List<DeviceDataBean.DustDataBean> data = bean.getDust_data();
        if (data != null && data.size() > 0) {
            cbcv_view.initData(data);
        } else {
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
        if (data != null) {
            if (pageSize == 0) {
                adapter.replaceData(bean.getDust_data());
            } else {
                adapter.addData(bean.getDust_data());
            }
        }
        if (data.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }
}
