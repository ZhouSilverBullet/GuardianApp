package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.TabTextBean;
import com.sdxxtop.guardianapp.presenter.GERPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GERContract;
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

    @Override
    protected int getLayout() {
        return R.layout.activity_grant_event_report;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initData() {
        super.initData();
        addLinearLayout();
    }

    private void addLinearLayout() {
        List<TabTextBean> data =  new ArrayList<>();
        data.add(new TabTextBean(1,"890","已上报"));
        data.add(new TabTextBean(2,"222","待处理"));
        data.add(new TabTextBean(3,"007","处理中"));
        data.add(new TabTextBean(4,"100","已处理"));
        data.add(new TabTextBean(5,"800","已完成"));
        for (int i = 0; i < data.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            TabTextBean tabTextBean = data.get(i);
            TabTextView tabTextView = new TabTextView(this);
            tabTextView.setLayoutParams(layoutParams);
            tabTextView.setValue(tabTextBean.getTitle(), tabTextBean.getDesc());
            tabTextView.setOnTabClickListener(i,this);
            if (i==data.size()-1){
                tabTextView.tvLine.setVisibility(View.GONE);
            }
            llLayout.addView(tabTextView);
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onTabClick(int num) {
        Intent intent = new Intent(this, EventStatistyActivity.class);
        intent.putExtra("eventId", num);
        startActivity(intent);
    }
}
