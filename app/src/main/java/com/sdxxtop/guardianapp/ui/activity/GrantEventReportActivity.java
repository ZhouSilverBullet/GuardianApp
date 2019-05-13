package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.GERPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GERContract;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;
import com.sdxxtop.guardianapp.ui.widget.TabTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GrantEventReportActivity extends BaseMvpActivity<GERPresenter> implements GERContract.IView, TabTextView.OnTabClickListener {

    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.pie_chart1)
    PieChartView pieChart1;
    @BindView(R.id.pie_chart2)
    PieChartView pieChart2;
    @BindView(R.id.time_select)
    GERTimeSelectView timeSelect;

    @Override
    protected int getLayout() {
        return R.layout.activity_grant_event_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        timeSelect.setOnTimeSelectListener(new GERTimeSelectView.OnTimeChooseListener() {
            @Override
            public void onTimeSelect(String startTime, String endTime) {
                mPresenter.index(startTime,endTime);
            }
        });
        pieChart1.setPieData(null);
        pieChart2.setPieData(null);
    }

    @Override
    protected void initData() {
        super.initData();
//        mPresenter.index(timeSelect.getSelectTime(1), timeSelect.getSelectTime(2));
        mPresenter.index("","");
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(this, EventStatistyActivity.class);
        intent.putExtra("event_type", num);
        startActivity(intent);
    }

    @Override
    public void showIndexData(GERPIndexBean indexBean) {
        addLinearLayout(indexBean);
        pieChart1.setPieData(indexBean.getEventInfo());  // 上报事件统计
        pieChart2.setPieData(indexBean.getCompleteInfo());  //已处理事件统计
    }

    private void addLinearLayout(GERPIndexBean indexBean) {
        List<TabTextBean> data = new ArrayList<>();
        llLayout.removeAllViews();
        data.add(new TabTextBean(0, String.valueOf(indexBean.getCount()), "已上报"));
        data.add(new TabTextBean(1, String.valueOf(indexBean.getWait_for()), "待处理"));
        data.add(new TabTextBean(2, String.valueOf(indexBean.getTo_solved()), "处理中"));
        data.add(new TabTextBean(3, String.valueOf(indexBean.getAdopt()), "已处理"));
        data.add(new TabTextBean(4, String.valueOf(indexBean.getPending()), "已完成"));
        for (int i = 0; i < data.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            TabTextBean tabTextBean = data.get(i);
            TabTextView tabTextView = new TabTextView(this);
            tabTextView.setLayoutParams(layoutParams);
            tabTextView.setValue(tabTextBean.getTitle(), tabTextBean.getDesc());
            tabTextView.setOnTabClickListener(i, this);
            if (i == data.size() - 1) {
                tabTextView.tvLine.setVisibility(View.GONE);
            }
            llLayout.addView(tabTextView);
        }
    }
}
