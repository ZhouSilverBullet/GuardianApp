package com.sdxxtop.guardianapp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.AssignListBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.presenter.WorkFragmentPresenter;
import com.sdxxtop.guardianapp.presenter.contract.WorkFragmentContract;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;
import com.sdxxtop.guardianapp.ui.adapter.NewDaiBanAdapter;
import com.sdxxtop.guardianapp.ui.adapter.WorkTabAdapter;
import com.sdxxtop.guardianapp.ui.assignevent.AssignEventActivity;
import com.sdxxtop.guardianapp.ui.assignevent.adapter.AssignListAdapter;
import com.sdxxtop.guardianapp.ui.widget.UnScrolGridView;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomOneBarChartView;
import com.sdxxtop.guardianapp.utils.UIUtils;

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_assign)
    RecyclerView recyclerViewAssign;
    @BindView(R.id.tv_chuli)
    TextView tvChuli;
    @BindView(R.id.tv_report)
    TextView tvReport;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.cbMyDBEvent)
    RadioButton cbMyDBEvent;
    @BindView(R.id.cbAssignEvent)
    RadioButton cbAssignEvent;


    private int currentSeletItem = 1;  // 1:事件 ， 2:交办事件
    private List<WorkIndexBean.PendingEvent> eventList = new ArrayList<>();  // 事件列表数据
    private List<AssignListBean.ListBean> assignList = new ArrayList<>();    // 交办列表数据

    private NewDaiBanAdapter adapter;
    private WorkTabAdapter tabAdapter;
    private AssignListAdapter assignAdapter;
    private int isAssignment;  // 交办事件的权限

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.loadWorkIndex();
        }
    }

    @Override
    protected void initData() {
        mPresenter.loadWorkIndex();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mPresenter.loadWorkIndex();
        }
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewDaiBanAdapter(null);
        recyclerView.setAdapter(adapter);
        tabAdapter = new WorkTabAdapter(getActivity());
        gridview.setAdapter(tabAdapter);

        recyclerViewAssign.setLayoutManager(new LinearLayoutManager(getContext()));
        assignAdapter = new AssignListAdapter(1);
        recyclerViewAssign.setAdapter(assignAdapter);

        cbMyDBEvent.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                currentSeletItem = 1;
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewAssign.setVisibility(View.GONE);
                if (eventList != null && eventList.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        });
        cbAssignEvent.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                currentSeletItem = 2;
                recyclerView.setVisibility(View.GONE);
                recyclerViewAssign.setVisibility(View.VISIBLE);
                if (assignList != null && assignList.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }
        });

        cbcvBarView.setOnClick(new CustomOneBarChartView.OnBarChartClick() {
            @Override
            public void barChartClick(String eventNum, String completeNum) {
                tvReport.setText(eventNum);
                tvChuli.setText(completeNum);
            }
        });
    }

    @OnClick({R.id.ll_more_event})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_more_event:
                Log.e("WorkFragment==", "" + currentSeletItem);
                if (currentSeletItem == 1) { // 选中 我的代办
                    startActivity(new Intent(getContext(), TaskAgentsActivity.class));
                } else {
                    Intent intent = new Intent(getContext(), AssignEventActivity.class);
                    intent.putExtra("isAssignment", isAssignment);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void showIndex(WorkIndexBean bean) {
        if (bean.month_complete != null && bean.month_complete.size() > 0) {
            cbcvBarView.initData(bean.month_complete, Color.parseColor("#442593E7"));
        } else {
            cbcvBarView.setNoData();
        }
        eventList = bean.pending_event;
        isAssignment = bean.is_assignment;
        if (eventList != null) {
            adapter.replaceData(eventList);
        }

        assignList = bean.list_assign;
        if (assignList != null) {
            assignAdapter.replaceData(assignList, true);
        }

        if (currentSeletItem == 1) {
            if (eventList.size() > 0) {
                tvNoData.setVisibility(View.GONE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        } else if (currentSeletItem == 2) {
            if (assignList.size() > 0) {
                tvNoData.setVisibility(View.GONE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }

        tvReport.setText("" + bean.report);
        tvChuli.setText("" + bean.complete);
        tvTitle.setText(bean.part_name);

        if (tabAdapter != null) {
            tabAdapter.setLimits(bean);
        }
    }
}
