package com.sdxxtop.guardianapp.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpFragment;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.presenter.PELPresenter;
import com.sdxxtop.guardianapp.presenter.contract.PELContract;
import com.sdxxtop.guardianapp.ui.activity.EventStatistyActivity;
import com.sdxxtop.guardianapp.ui.adapter.PartEventListAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :  lwb
 * Date: 2019/10/15
 * Desc:
 */
public class EventListFragment extends BaseMvpFragment<PELPresenter> implements PELContract.IView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.gertsv_view)
    GERTimeSelectView gertsvView;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;
    @BindView(R.id.ll_temp)
    LinearLayout llTemp;
    @BindView(R.id.tv_bg)
    TextView tvBg;

    private String start_time = "";
    private String end_time = "";
    private int part_id = 1;
    private int event_type;

    private PartEventListAdapter adapter;
    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowStatusData = new ArrayList<>();

    public static EventListFragment newInstance(int event_type) {
        Bundle args = new Bundle();
        args.putInt("eventStatus", event_type);
        EventListFragment fragment = new EventListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_event_list;
    }

    @Override
    protected void initView() {
        EventStatistyActivity activity = (EventStatistyActivity) getActivity();
        if (activity != null) {
            start_time = activity.startTime;
            end_time = activity.endTime;
        }
        //初始化时间
        if (!TextUtils.isEmpty(start_time) && !TextUtils.isEmpty(end_time)) {
            gertsvView.tvStartTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvEndTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvStartTime.setText(start_time.split(" ")[0].replace("-", "/"));
            gertsvView.tvEndTime.setText(end_time.split(" ")[0].replace("-", "/"));

            gertsvView.setTime(start_time, end_time);
        }

        event_type = getArguments().getInt("eventStatus");
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter != null) {
                    mPresenter.postPartEventList(adapter.getItemCount(), part_id, start_time, end_time, event_type);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.postPartEventList(0, part_id, start_time, end_time, event_type);
            }
        });
        smartRefresh.autoRefresh();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PartEventListAdapter();
        recyclerView.setAdapter(adapter);

        //日期选择
        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                start_time = startTime;
                end_time = endTime;
                mPresenter.postPartEventList(0, part_id, start_time, end_time, event_type);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @OnClick({R.id.ll_containor_temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_containor_temp:
                AreaSelectPopWindow popWindow = new AreaSelectPopWindow(getActivity(), llTemp, popWondowStatusData, tvStatus, tvBg, event_type);
                popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int part_typeid, String partName) {
                        tvStatus.setText(partName);
                        event_type = part_typeid;
                        mPresenter.postPartEventList(0, part_id, start_time, end_time, event_type);
                    }
                });
                break;
        }
    }

    @Override
    public void showData(PartEventListBean bean, int start_page) {
        if (smartRefresh != null) {
            smartRefresh.finishLoadMore();
            smartRefresh.finishRefresh();
        }
        if (start_page == 0) {
            adapter.replaceData(bean.getCl_data());
        } else {
            adapter.addData(bean.getCl_data());
        }

        popWondowStatusData.clear();
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(0, "全部"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(1, "待处理"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(2, "处理中"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(3, "待验收"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(4, "已完成"));
    }

    public void loadData(int partId, int eventStatus) {
        this.part_id = partId;
        event_type = eventStatus;
        switch (eventStatus) {
            case 0:
                tvStatus.setText("全部");
                break;
            case 1:
                tvStatus.setText("待处理");
                break;
            case 2:
                tvStatus.setText("处理中");
                break;
            case 3:
                tvStatus.setText("待验收");
                break;
            case 4:
                tvStatus.setText("已完成");
                break;
        }
        mPresenter.postPartEventList(0, part_id, "", "", event_type);
    }
}
