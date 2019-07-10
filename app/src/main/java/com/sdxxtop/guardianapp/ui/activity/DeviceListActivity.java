package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.DeviceListBean;
import com.sdxxtop.guardianapp.presenter.DeviceListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DeviceListContract;
import com.sdxxtop.guardianapp.ui.adapter.DeviceListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class DeviceListActivity extends BaseMvpActivity<DeviceListPresenter> implements DeviceListContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_area_layout)
    LinearLayout llAreaLayout;
    @BindView(R.id.ll_status_layout)
    LinearLayout llStatusLayout;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.titleView)
    TitleView titleView;

    private DeviceListAdapter adapter;

    private List<AreaSelectPopWindow.PopWindowDataBean> areaList = new ArrayList<>();
    private List<AreaSelectPopWindow.PopWindowDataBean> statusList = new ArrayList<>();

    private int partId = 0;
    private int typeId = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_device_list;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();
        String status = getIntent().getStringExtra("status");
        tvStatus.setText(status);
        typeId = getStatus(status);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeviceListAdapter(R.layout.item_device_info, null);
        recyclerView.setAdapter(adapter);
        //设备状态 1、正常 2、预警 3设备异常
        statusList.add(new AreaSelectPopWindow.PopWindowDataBean(0, "全部"));
        statusList.add(new AreaSelectPopWindow.PopWindowDataBean(1, "正常"));
        statusList.add(new AreaSelectPopWindow.PopWindowDataBean(2, "预警"));
        statusList.add(new AreaSelectPopWindow.PopWindowDataBean(3, "设备异常"));

        titleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceListActivity.this, MonitorMapActivity.class);
                startActivity(intent);
            }
        });
    }

    private int getStatus(String status) {
        int result = 0;
        switch (status) {
            case "全部":
                result = 0;
                break;
            case "正常":
                result = 1;
                break;
            case "预警":
                result = 2;
                break;
            case "设备异常":
                result = 3;
                break;
        }
        return result;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData(partId, typeId);
    }

    @Override
    public void showData(DeviceListBean bean) {
        List<DeviceListBean.DeviceInfoBean> deviceInfoList = bean.getDevice_info();
        List<DeviceListBean.PartInfoBean> partInfoList = bean.getPart_info();
        if (deviceInfoList != null) {
            adapter.replaceData(deviceInfoList);
        }
        areaList.clear();
        if (partInfoList != null) {
            for (DeviceListBean.PartInfoBean partInfoBean : partInfoList) {
                areaList.add(new AreaSelectPopWindow.PopWindowDataBean(partInfoBean.getPart_id(), partInfoBean.getPart_name()));
            }
        }
    }

    @OnClick({R.id.ll_area_layout, R.id.ll_status_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_area_layout:
                AreaSelectPopWindow popWindow = new AreaSelectPopWindow(DeviceListActivity.this, llContainor, areaList, tvArea, tvBg);
                popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int part_typeid, String partName) {
                        tvArea.setText(partName);
                        tvStatus.setText("全部");
                        partId = part_typeid;
                        mPresenter.loadData(partId, 0);
                    }
                });
                break;
            case R.id.ll_status_layout:
                AreaSelectPopWindow popWindowStatus = new AreaSelectPopWindow(DeviceListActivity.this, llContainor, statusList, tvStatus, tvBg);
                popWindowStatus.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int part_typeid, String partName) {
                        tvStatus.setText(partName);
                        typeId = part_typeid;
                        mPresenter.loadData(partId, typeId);
                    }
                });
                break;
        }
    }
}
