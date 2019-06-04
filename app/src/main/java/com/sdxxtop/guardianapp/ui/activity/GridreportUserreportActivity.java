package com.sdxxtop.guardianapp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.presenter.GridreportUserreportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridreportUserreportContract;
import com.sdxxtop.guardianapp.ui.adapter.GridreportUserreportAdapter;
import com.sdxxtop.guardianapp.ui.pop.AreaSelectPopWindow;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class GridreportUserreportActivity extends BaseMvpActivity<GridreportUserreportPresenter> implements GridreportUserreportContract.IView {

    @BindView(R.id.title)
    TitleView title;
    @BindView(R.id.gertsv_view)
    GERTimeSelectView gertsvView;
    @BindView(R.id.ll_containor)
    LinearLayout llContainor;
    @BindView(R.id.ll_containor_temp)
    LinearLayout llContainorTemp;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_bg)
    TextView tvBg;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    private int part_userid;  //查看的用户id
    private String start_time, end_time;  //查看的用户id
    private GridreportUserreportAdapter adapter;
    private boolean isOrder;  // 是否倒序
    private List<AreaSelectPopWindow.PopWindowDataBean> popWondowStatusData = new ArrayList<>();
    private int event_type;

    @Override
    protected int getLayout() {
        return R.layout.activity_gridreport_userreport;
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
        title.setTitleValue("上报事件");

        popWondowStatusData.clear();
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(0, "全部"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(1, "待处理"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(2, "处理中"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(3, "已处理"));
        popWondowStatusData.add(new AreaSelectPopWindow.PopWindowDataBean(4, "已完成"));

        part_userid = getIntent().getIntExtra("part_userid", -1);
        start_time = getIntent().getStringExtra("startTime");
        end_time = getIntent().getStringExtra("endTime");

        if (!TextUtils.isEmpty(start_time) && !TextUtils.isEmpty(end_time)) {
            gertsvView.tvStartTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvEndTime.setTextColor(getResources().getColor(R.color.black));
            gertsvView.tvStartTime.setText(start_time.split(" ")[0].replace("-", "/"));
            gertsvView.tvEndTime.setText(end_time.split(" ")[0].replace("-", "/"));

            gertsvView.setTime(start_time, end_time);
        }

        smartRefresh.setEnableRefresh(true);
        smartRefresh.setEnableLoadMore(true);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (adapter!=null){
                    mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, adapter.getItemCount(),event_type);
                }

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, 0,event_type);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GridreportUserreportAdapter(R.layout.item_gridreport_userreport, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.autoRefresh();

        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                start_time = startTime;
                end_time = endTime;
                mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, 0,event_type);
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        llContainor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<GridreportUserreportBean.ClData> data = adapter.getData();
//                Collections.sort(data, new GridreportUserreportCompar(isOrder));
//                adapter.replaceData(data);
//                isOrder=!isOrder;
                AreaSelectPopWindow popWindow = new AreaSelectPopWindow(GridreportUserreportActivity.this, llContainorTemp, popWondowStatusData, tvBg, tvBg, event_type);
                popWindow.setOnPopItemClickListener(new AreaSelectPopWindow.OnPopItemClickListener() {
                    @Override
                    public void onPopItemClick(int part_typeid, String partName) {
                        event_type = part_typeid;
                        tvStatus.setText(partName);
                        mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, 0, event_type);
                    }
                });
            }
        });
    }

    @Override
    public void showData(GridreportUserreportBean bean,int start_page) {
        if (smartRefresh!=null){
            smartRefresh.finishLoadMore();
            smartRefresh.finishRefresh();
        }

        if (start_page==0){
            adapter.replaceData(bean.getCl_data());
        }else{
            adapter.addData(bean.getCl_data());
        }

    }
}
