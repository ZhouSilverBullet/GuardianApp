package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.presenter.MyAssessPresenter;
import com.sdxxtop.guardianapp.presenter.contract.MyAssessContract;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MyAssessCalendarView;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomOneBarChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sdxxtop.guardianapp.ui.activity.kaoqin.AssessDetailActivity.generateCenterSpannableText;

public class MyAssessActivity extends BaseMvpActivity<MyAssessPresenter> implements MyAssessContract.IView {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.cbcv_bar_view)
    CustomOneBarChartView cbcvBarView;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.my_assess_1)
    MyAssessLayout myAssess1;
    @BindView(R.id.my_assess_2)
    MyAssessLayout myAssess2;
    @BindView(R.id.my_assess_3)
    MyAssessLayout myAssess3;
    @BindView(R.id.pieChartView)
    PieChartView pieChartView;
    @BindView(R.id.mcv_view)
    MyAssessCalendarView mcv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_assess;
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
        List<WorkIndexBean.MonthComplete> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            WorkIndexBean.MonthComplete bean = new WorkIndexBean.MonthComplete();
            bean.events_complete = i;
            bean.events_count = i;
            bean.complete_rate = i;
            data.add(bean);
        }
        cbcvBarView.initData(data, Color.parseColor("#442593E7"));

        initPieChart();
    }

    @OnClick({R.id.my_assess_1, R.id.my_assess_2, R.id.my_assess_3, R.id.tv_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_assess_1:  // 我的考勤
                startActivity(new Intent(this, MineAttendanceActivity.class));
                break;
            case R.id.my_assess_2:  // 我的工作
                startActivity(new Intent(this, MyWorkActivity.class));
                break;
            case R.id.my_assess_3:  // 绩效明细
                startActivity(new Intent(this, AssessDetailActivity.class));
                break;
            case R.id.tv_change:
                cbcvBarView.setVisibility(cbcvBarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                pieChartView.setVisibility(pieChartView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void initPieChart() {
        PieChart chart = pieChartView.getPieChart();
        pieChartView.isDrawValue(false);   // 不绘制值
        chart.setHoleRadius(60f);
        chart.setExtraOffsets(0f, 0f, 0f, 10f);
        chart.setRotationEnabled(false);  // 进制转动
        chart.setCenterText(generateCenterSpannableText());
        chart.getLegend().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setDrawCenterText(true);

        List<EventChartBean.ChartInfoBean> data = new ArrayList<>();
        EventChartBean.ChartInfoBean bean = new EventChartBean.ChartInfoBean();
        bean.setColor("#3296FA");
        bean.setCount(60);
        bean.setNum(60);
        bean.setPart_name("考勤部分 30");
        bean.setPart_id(2);
        data.add(bean);

        EventChartBean.ChartInfoBean bean2 = new EventChartBean.ChartInfoBean();
        bean2.setColor("#F39826");
        bean2.setCount(30);
        bean2.setNum(30);
        bean2.setPart_name("工作部分 60");
        bean2.setPart_id(1);
        data.add(bean2);

        pieChartView.setPieData(data);
    }
}
