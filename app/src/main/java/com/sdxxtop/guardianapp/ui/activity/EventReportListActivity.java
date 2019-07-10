package com.sdxxtop.guardianapp.ui.activity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.presenter.TaskAgentsPresenter;
import com.sdxxtop.guardianapp.presenter.contract.TaskAgentsContract;
import com.sdxxtop.guardianapp.ui.adapter.EventReportListAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 名字应该取 EventReportListActivity
 */
public class EventReportListActivity extends BaseMvpActivity<TaskAgentsPresenter> implements TaskAgentsContract.IView {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private EventReportListAdapter mAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report_list;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new EventReportListAdapter(R.layout.item_event_report_list_recycler);
        mRecyclerView.setAdapter(mAdapter);
//        ArrayList<String> objects = new ArrayList<>();
//        objects.add("");
//        objects.add("");
//        objects.add("");
//        mAdapter.addData(objects);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(mAdapter.getData().size(), 2);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(0, 2);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData(0, 2);
    }

    @Override
    public void showData(int page, EventIndexBean eventIndexBean) {
        List<EventIndexBean.EventBean> event = eventIndexBean.getEvent();
        if (event == null) {
            return;
        }

        if (page == 0 && event.size() != 0) {
            mAdapter.replaceData(event);
        }

        if (page != 0) {
            mAdapter.addData(event);
        }

        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
