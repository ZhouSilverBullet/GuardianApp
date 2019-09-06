package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.presenter.WorkFragmentPresenter;
import com.sdxxtop.guardianapp.presenter.contract.WorkFragmentContract;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;
import com.sdxxtop.guardianapp.ui.adapter.NewDaiBanAdapter;
import com.sdxxtop.guardianapp.ui.adapter.WorkTabAdapter;
import com.sdxxtop.guardianapp.ui.widget.UnScrolGridView;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomOneBarChartView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class WorkFragment extends BaseMvpFragment<WorkFragmentPresenter> implements WorkFragmentContract.IView {


    @BindView(R.id.gridview)
    UnScrolGridView gridview;
    @BindView(R.id.cbcv_bar_view)
    CustomOneBarChartView cbcvBarView;
    @BindView(R.id.ll_more_event)
    LinearLayout llMoreEvent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_chuli)
    TextView tvChuli;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private NewDaiBanAdapter adapter;
    private WorkTabAdapter tabAdapter;

    public static WorkFragment newInstance() {
        Bundle args = new Bundle();
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_work;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadWorkIndex();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.loadWorkIndex();
        }
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewDaiBanAdapter(null);
        recyclerView.setAdapter(adapter);
        tabAdapter = new WorkTabAdapter();
        gridview.setAdapter(tabAdapter);
    }

    @OnClick(R.id.ll_more_event)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_more_event:
                startActivity(new Intent(getContext(), TaskAgentsActivity.class));
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showIndex(WorkIndexBean bean) {
        List<Float> list = new ArrayList<>();
        if (bean.month_complete != null && bean.month_complete.size() > 0) {
            for (WorkIndexBean.MonthComplete item : bean.month_complete) {
                list.add(item.complete_rate);
            }
            cbcvBarView.initData(list, Color.parseColor("#0F2593E7"));
        } else {
            cbcvBarView.setNoData();
        }
        adapter.replaceData(bean.pending_event);
        tvReport.setText("" + bean.report);
        tvChuli.setText("" + bean.complete);
        tvTitle.setText(bean.part_name);

        if (tabAdapter != null) {
            tabAdapter.setLimits(bean);
        }
    }
}
