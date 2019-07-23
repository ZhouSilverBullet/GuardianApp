package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.GERPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GERContract;
import com.sdxxtop.guardianapp.ui.widget.CustomEventLayout;
import com.sdxxtop.guardianapp.ui.widget.GERTimeSelectView;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GrantEventReportActivity extends BaseMvpActivity<GERPresenter> implements GERContract.IView, CustomEventLayout.OnTabClickListener {

    @BindView(R.id.cel_view)
    CustomEventLayout celView;
    @BindView(R.id.pie_chart1)
    PieChartView pieChart1;
    @BindView(R.id.pie_chart2)
    PieChartView pieChart2;
    @BindView(R.id.time_select)
    GERTimeSelectView timeSelect;

    private String mStartTime, mEndTime;
    private List<String> partId = new ArrayList<>();
    private int currentItem = 0;

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
                mStartTime = startTime;
                mEndTime = endTime;
                mPresenter.index(mStartTime, mEndTime);
                if (partId.size()==0){
                    return;
                }
                mPresenter.eventChart(mStartTime, mEndTime,partId.get(partId.size()-1),false);
            }
        });
        pieChart1.setPieData(null);
        pieChart2.setPieData(null);

        celView.setOnTabClickListener(this);

        List<TabTextBean> data = new ArrayList<>();
        data.add(new TabTextBean(0, "--", "已上报"));
        data.add(new TabTextBean(1, "--", "待处理"));
        data.add(new TabTextBean(2, "--", "处理中"));
        data.add(new TabTextBean(3, "--", "待验收"));
        data.add(new TabTextBean(4, "--", "已完成"));
        celView.addLayout(data);
        pieChart1.setOnPieChartClick(new PieChartView.OnPieChartClick() {
            @Override
            public void pieItemClick(String id) {
                pieChart1.tv_up.setVisibility(View.VISIBLE);
                mPresenter.eventChart(mStartTime, mEndTime, id, true);
            }
        });
        pieChart2.setOnPieChartClick(new PieChartView.OnPieChartClick() {
            @Override
            public void pieItemClick(String id) {
                pieChart1.tv_up.setVisibility(View.VISIBLE);
                mPresenter.eventChart(mStartTime, mEndTime, id, true);
            }
        });

        pieChart1.tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem == 1) {
                    return;
                }
                currentItem -= 1;
                partId.remove(currentItem);
                if (currentItem == 1){
                    pieChart1.tv_up.setVisibility(View.GONE);
                }
                mPresenter.eventChart(mStartTime, mEndTime, partId.get(partId.size() - 1), false);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.index(mStartTime, mEndTime);
        mPresenter.eventChart(mStartTime, mEndTime, "0", true);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(this, EventStatistyActivity.class);
        intent.putExtra("event_type", num);
        intent.putExtra("startTime", mStartTime);
        intent.putExtra("endTime", mEndTime);
        startActivity(intent);
    }

    @Override
    public void showIndexData(GERPIndexBean indexBean) {
        addLinearLayout(indexBean);
//        List<GERPIndexBean.EventInfoBean> pieOneData = new ArrayList<>();
//        List<GERPIndexBean.EventInfoBean> pieTwoData = new ArrayList<>();
//
//        if (indexBean.getEventInfo()!=null&&indexBean.getEventInfo().size()>0){
//            for (GERPIndexBean.EventInfoBean bean : indexBean.getEventInfo()) {
//                if (bean.getCount()!=0){
//                    pieOneData.add(bean);
//                }
//            }
//        }
//        if (indexBean.getCompleteInfo()!=null&&indexBean.getCompleteInfo().size()>0){
//            for (GERPIndexBean.EventInfoBean bean : indexBean.getCompleteInfo()) {
//                if (bean.getCount()!=0){
//                    pieTwoData.add(bean);
//                }
//            }
//        }
//        pieChart1.setPieData(indexBean.getEventInfo());  // 上报事件统计
//        pieChart2.setPieData(indexBean.getCompleteInfo());  //待验收事件统计
    }

    @Override
    public void showChartData(EventChartBean bean, String chartId, boolean isAdd) {
        if (bean.getEventInfo().size() == 0 && bean.getCompleteInfo().size() == 0) {
            showToast("该部门下无子部门");
            return;
        }
        if (isAdd) {
            partId.add(chartId);
            currentItem += 1;
        }

        pieChart1.setPieData(bean.getEventInfo());  // 上报事件统计
        pieChart2.setPieData(bean.getCompleteInfo());  //待验收事件统计
    }

    private void addLinearLayout(GERPIndexBean indexBean) {
        List<TabTextBean> data = new ArrayList<>();
        data.add(new TabTextBean(0, String.valueOf(indexBean.getCount()), "已上报"));
        data.add(new TabTextBean(1, String.valueOf(indexBean.getWait_for()), "待处理"));
        data.add(new TabTextBean(2, String.valueOf(indexBean.getTo_solved()), "处理中"));
        data.add(new TabTextBean(3, String.valueOf(indexBean.getAdopt()), "待验收"));
        data.add(new TabTextBean(4, String.valueOf(indexBean.getPending()), "已完成"));
        celView.addLayout(data);
    }
}
