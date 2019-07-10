package com.sdxxtop.guardianapp.ui.activity;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;
import com.sdxxtop.guardianapp.presenter.EventDiscretionListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionListContract;
import com.sdxxtop.guardianapp.ui.adapter.EventDiscretionListAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class EventDiscretionListActivity extends BaseMvpActivity<EventDiscretionListPresenter> implements EventDiscretionListContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private EventDiscretionListAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_discretion_list;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData(0);
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new EventDiscretionListAdapter(R.layout.item_event_discretion_list, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(mAdapter.getData().size());
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mPresenter != null) {
                    mPresenter.loadData(0);
                }
            }
        });
    }

    @Override
    public void showData(int page, EventDiscretionListBean bean) {
        List<EventDiscretionListBean.PartolBean> event = bean.getPartol();
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
