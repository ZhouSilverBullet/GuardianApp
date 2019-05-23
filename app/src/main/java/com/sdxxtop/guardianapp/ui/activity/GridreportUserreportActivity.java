package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.model.bean.compar.GridreportUserreportCompar;
import com.sdxxtop.guardianapp.presenter.GridreportUserreportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GridreportUserreportContract;
import com.sdxxtop.guardianapp.ui.adapter.GridreportUserreportAdapter;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.Collections;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private int part_userid;  //查看的用户id
    private String start_time, end_time;  //查看的用户id
    private int start_page;
    private GridreportUserreportAdapter adapter;
    private boolean isOrder;  // 是否倒序

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
        part_userid = getIntent().getIntExtra("part_userid", -1);
        start_time = getIntent().getStringExtra("startTime");
        end_time = getIntent().getStringExtra("endTime");


        smartRefresh.setEnableRefresh(true);
        smartRefresh.setEnableLoadMore(true);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                start_page = adapter.getItemCount();
                mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, start_page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                start_page = 0;
                mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, start_page);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GridreportUserreportAdapter(R.layout.item_gridreport_userreport, null);
        recyclerView.setAdapter(adapter);

        smartRefresh.autoRefresh();

        gertsvView.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                start_page = 0;
                start_time = startTime;
                end_time = endTime;
                mPresenter.gridreportUserreport(GridreportUserreportActivity.this.part_userid, start_time, end_time, start_page);
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        llContainor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GridreportUserreportBean.ClData> data = adapter.getData();
                Collections.sort(data, new GridreportUserreportCompar(isOrder));
                adapter.replaceData(data);
                isOrder=!isOrder;
            }
        });
    }

    @Override
    public void showData(GridreportUserreportBean bean) {
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
