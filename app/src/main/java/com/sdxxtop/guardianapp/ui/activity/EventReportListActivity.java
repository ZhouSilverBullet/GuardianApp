package com.sdxxtop.guardianapp.ui.activity;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventReportListPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportListContract;
import com.sdxxtop.guardianapp.ui.adapter.EventReportListAdapter;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 名字应该取 EventReportListActivity
 */
public class EventReportListActivity extends BaseMvpActivity<EventReportListPresenter> implements EventReportListContract.IView {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TitleView mTitleView;
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
        ArrayList<String> objects = new ArrayList<>();
        objects.add("");
        objects.add("");
        objects.add("");
        mAdapter.addData(objects);
    }
}
