package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.model.bean.RecordIndexBean;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.model.http.callback.IRequestCallback;
import com.sdxxtop.guardianapp.model.http.net.Params;
import com.sdxxtop.guardianapp.model.http.util.RxUtils;
import com.sdxxtop.guardianapp.presenter.MyAssessPresenter;
import com.sdxxtop.guardianapp.presenter.contract.MyAssessContract;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.MonthSelectView;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.calendar.YearSelectView;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.ui.widget.chart.CustomOneBarChartView;
import com.sdxxtop.guardianapp.ui.widget.chart.KHMarkerView;
import com.sdxxtop.guardianapp.ui.widget.imgservice.OnlineServiceActivity;
import com.sdxxtop.guardianapp.utils.Date2Util;
import com.sdxxtop.guardianapp.utils.SingleClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static com.sdxxtop.guardianapp.model.http.net.RetrofitHelper.getEnvirApi;


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
    @BindView(R.id.msv_view)
    MonthSelectView msv_view;
    @BindView(R.id.ysvView)
    YearSelectView ysvView;

    private boolean isBarChartShow = false; // 是否显示了饼状图
    private boolean isFirst = true; // 是否是第一次加载数据

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
        initPieChart();
        titleView.getTvRight().setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                Intent intent = new Intent(mContext, OnlineServiceActivity.class);
                intent.putExtra("href", "http://wap.sdxxtop.com/Assessment/index/mine.html");
                intent.putExtra("isShowTitile", true);
                startActivity(intent);
            }
        });
        msv_view.setOnMonthChageListener((year, month) -> loadData(Date2Util.dateToStamp("" + year + "-" + month)));
        ysvView.setOnMonthChageListener(year -> loadData(Date2Util.dateToStamp("" + year)));
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
                tvChange.setText(cbcvBarView.getVisibility() == View.GONE ? "图表" : "分值");
                msv_view.setVisibility(pieChartView.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                ysvView.setVisibility(cbcvBarView.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                isBarChartShow = cbcvBarView.getVisibility() == View.VISIBLE;
                break;
        }
    }

    private void initPieChart() {
        PieChart chart = pieChartView.getPieChart();
        pieChartView.isDrawValue(false);   // 不绘制值
        chart.setHoleRadius(60f);
        chart.setExtraOffsets(0f, 0f, 0f, 10f);
        chart.setRotationEnabled(false);  // 进制转动
        //chart.setCenterText(generateCenterSpannableText());
        chart.getLegend().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setDrawCenterText(true);

        //设置BarChart柱状图点击事件
        BarChart barChart = cbcvBarView.getBarChart();
        KHMarkerView mv = new KHMarkerView(this, (value, axis) -> (int) (value) + "");
        mv.setChartView(barChart);
        barChart.setMarker(mv);
    }

    @Override
    protected void initData() {
        loadData(Date2Util.dateToStamp("" + msv_view.year + "-" + msv_view.month));
    }

    /**
     * 加载数据
     */
    private void loadData(String date) {
        Params params = new Params();
        params.put("de", date);
        Observable<RequestBean<RecordIndexBean>> observable = getEnvirApi().getRecordIndex(params.getData());
        Disposable disposable = RxUtils.handleDataHttp(observable, new IRequestCallback<RecordIndexBean>() {
            @Override
            public void onSuccess(RecordIndexBean bean) {
                bandData(bean);
            }

            @Override
            public void onFailure(int code, String error) {

            }
        });

    }

    /**
     * 绑定饼图/柱图数据
     *
     * @param bean 日常考勤:#2593E7
     *             巡查走访:#EC6941
     *             其他部分:#07A01E
     *             问题上报:#E72A3A
     */
    private void bandData(RecordIndexBean bean) {
        if (isFirst) {
            isFirst = false;
            List<EventChartBean.ChartInfoBean> data = new ArrayList<>();
            EventChartBean.ChartInfoBean rckq = new EventChartBean.ChartInfoBean();
            rckq.setColor("#2593E7");
            rckq.setCount((int) bean.lists.usually_score);
            rckq.setNum((int) bean.lists.usually_score);
            rckq.setPart_name("日常考勤 " + bean.lists.usually_score);
            data.add(rckq);

            EventChartBean.ChartInfoBean xczf = new EventChartBean.ChartInfoBean();
            xczf.setColor("#EC6941");
            xczf.setCount((int) bean.lists.patrol_score);
            xczf.setNum((int) bean.lists.patrol_score);
            xczf.setPart_name("巡查走访 " + bean.lists.patrol_score);
            data.add(xczf);

            EventChartBean.ChartInfoBean qtbf = new EventChartBean.ChartInfoBean();
            qtbf.setColor("#07A01E");
            qtbf.setCount((int) bean.lists.other_score);
            qtbf.setNum((int) bean.lists.other_score);
            qtbf.setPart_name("其他部分 " + bean.lists.other_score);
            data.add(qtbf);

            EventChartBean.ChartInfoBean wtsb = new EventChartBean.ChartInfoBean();
            wtsb.setColor("#E72A3A");
            wtsb.setCount((int) bean.lists.matter_score);
            wtsb.setNum((int) bean.lists.matter_score);
            wtsb.setPart_name("问题上报 " + bean.lists.matter_score);
            data.add(wtsb);

            pieChartView.setPieData(data);
            pieChartView.setDescValue(bean.lists);

            if (bean.echarts == null) return;
            List<WorkIndexBean.MonthComplete> barData = new ArrayList<>();
            for (RecordIndexBean.EchartsBean item : bean.echarts) {
                WorkIndexBean.MonthComplete itemData = new WorkIndexBean.MonthComplete();
                itemData.complete_rate = item.score;
                barData.add(itemData);
            }
            cbcvBarView.initData(barData, Color.parseColor("#442593E7"));
            return;
        }
        if (isBarChartShow) {
            if (bean.echarts == null) return;
            List<WorkIndexBean.MonthComplete> barData = new ArrayList<>();
            for (RecordIndexBean.EchartsBean item : bean.echarts) {
                WorkIndexBean.MonthComplete itemData = new WorkIndexBean.MonthComplete();
                itemData.complete_rate = item.score;
                barData.add(itemData);
            }
            cbcvBarView.initData(barData, Color.parseColor("#442593E7"));
        } else {
            List<EventChartBean.ChartInfoBean> data = new ArrayList<>();
            EventChartBean.ChartInfoBean rckq = new EventChartBean.ChartInfoBean();
            rckq.setColor("#2593E7");
            rckq.setCount((int) bean.lists.usually_score);
            rckq.setNum((int) bean.lists.usually_score);
            rckq.setPart_name("日常考勤 " + bean.lists.usually_score);
            data.add(rckq);

            EventChartBean.ChartInfoBean xczf = new EventChartBean.ChartInfoBean();
            xczf.setColor("#EC6941");
            xczf.setCount((int) bean.lists.patrol_score);
            xczf.setNum((int) bean.lists.patrol_score);
            xczf.setPart_name("巡查走访 " + bean.lists.patrol_score);
            data.add(xczf);

            EventChartBean.ChartInfoBean qtbf = new EventChartBean.ChartInfoBean();
            qtbf.setColor("#07A01E");
            qtbf.setCount((int) bean.lists.other_score);
            qtbf.setNum((int) bean.lists.other_score);
            qtbf.setPart_name("其他部分 " + bean.lists.other_score);
            data.add(qtbf);

            EventChartBean.ChartInfoBean wtsb = new EventChartBean.ChartInfoBean();
            wtsb.setColor("#E72A3A");
            wtsb.setCount((int) bean.lists.matter_score);
            wtsb.setNum((int) bean.lists.matter_score);
            wtsb.setPart_name("问题上报 " + bean.lists.matter_score);
            data.add(wtsb);

            pieChartView.setPieData(data);
            pieChartView.setDescValue(bean.lists);
        }
    }

}
