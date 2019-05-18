package com.sdxxtop.guardianapp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.model.bean.compar.PartEventListCompar;
import com.sdxxtop.guardianapp.presenter.PELPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;
import com.sdxxtop.guardianapp.ui.adapter.PartEventListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class PartEventListActivity extends BaseMvpActivity<PELPresenter> implements PELContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.casv_view)
    CustomAreaSelectView casvView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.gertsv_view)
    GERTimeSelectView gertsvView;
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;

    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowData = new ArrayList<>();

    private PartEventListAdapter adapter;
    private String part_id;  // 部门id
    private int event_type = 0;
    private String start_time, end_time;
    private int start_page;  // 分页请求
    private boolean isOrder; // 排序

    @Override
    protected int getLayout() {
        return R.layout.activity_part_event_list;
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
    }

    @Override
    protected void initView() {
        super.initView();
        part_id = getIntent().getStringExtra("part_id");
        start_time = getIntent().getStringExtra("startTime");
        end_time = getIntent().getStringExtra("endTime");
        event_type = getIntent().getIntExtra("status", 0);

        if (!TextUtils.isEmpty(start_time) && !TextUtils.isEmpty(end_time)) {
            gertsvView.tvStartTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvEndTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvStartTime.setText(start_time.split(" ")[0].replace("-", "/"));
            gertsvView.tvEndTime.setText(end_time.split(" ")[0].replace("-", "/"));

            gertsvView.setTime(start_time, end_time);
        }

        title.setTitleValue(getIntent().getStringExtra("part_name") + "事件");
        smartRefresh.setEnableLoadMore(true);
        smartRefresh.setEnableRefresh(true);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    start_page = adapter.getItemCount();
                    mPresenter.postPartEventList(start_page, part_id, start_time, end_time, event_type);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start_page = 0;
                mPresenter.postPartEventList(start_page, part_id, start_time, end_time, event_type);
            }
        });
        smartRefresh.autoRefresh();

        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                start_page = 0;
                start_time = startTime;
                end_time = endTime;
                mPresenter.postPartEventList(start_page, part_id, start_time, end_time, event_type);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PartEventListAdapter(R.layout.item_part_event, null);
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.casv_view, R.id.ll_containor_temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.casv_view:
                new AreaSelectPopWindow(PartEventListActivity.this, casvView.llAreaLayout, popWondowData, casvView.tvArea);
                break;
            case R.id.ll_containor_temp:
                List<PartEventListBean.ClData> data = adapter.getData();
                Collections.sort(data, new PartEventListCompar(isOrder));
                adapter.replaceData(data);
                isOrder = !isOrder;
                break;
        }
    }

    @Override
    public void showData(PartEventListBean bean) {
        if (smartRefresh != null) {
            smartRefresh.finishLoadMore();
            smartRefresh.finishRefresh();
        }
        if (start_page == 0) {
            adapter.replaceData(bean.getCl_data());
        } else {
            adapter.addData(bean.getCl_data());
        }
        popWondowData.clear();
        for (int i = 0; i < bean.getPart_name().size(); i++) {
            popWondowData.add(new AreaSelectPopWindow.PopWindowDataBean(0, bean.getPart_name().get(i).getPart_name()));
        }
    }
}
