package com.sdxxtop.guardianapp.ui.activity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.presenter.PELPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;
import com.sdxxtop.guardianapp.ui.adapter.PartEventListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.CustomAreaSelectView;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
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

    private List<EventListBean.CompleteInfo> data = new ArrayList<>();

    private PartEventListAdapter adapter;
    private String part_id;  // 部门id
    private int event_type = 0;
    private String startTime, endTime;
    private int start_page = 0;

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
        title.setTitleValue(getIntent().getStringExtra("part_name") + "事件");
        smartRefresh.setEnableLoadMore(true);
        smartRefresh.setEnableRefresh(true);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPresenter.postPartEventList(adapter.getItemCount(), part_id, startTime, endTime, event_type);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.postPartEventList(0, part_id, startTime, endTime, event_type);
            }
        });
        smartRefresh.autoRefresh();

        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                mPresenter.postPartEventList(adapter.getItemCount(), part_id, startTime, endTime, event_type);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PartEventListAdapter(R.layout.item_part_event, null);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.casv_view)
    public void onViewClicked() {
        new AreaSelectPopWindow(PartEventListActivity.this, casvView.llAreaLayout, data, casvView.tvArea);
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
        data.clear();
        data.add(new EventListBean.CompleteInfo(0, "全区", -1));
        for (int i = 0; i < bean.getPart_name().size(); i++) {
            data.add(new EventListBean.CompleteInfo(0, bean.getPart_name().get(i).getPart_name(), -1));
        }
    }
}
