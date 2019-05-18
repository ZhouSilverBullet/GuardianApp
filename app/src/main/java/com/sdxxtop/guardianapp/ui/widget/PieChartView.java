package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GERPIndexBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :  lwb
 * Date: 2019/5/9
 * Desc:
 */
public class PieChartView extends LinearLayout implements OnChartValueSelectedListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.tv_nodata)
    TextView tvDodata;

    private String title;

    protected String[] mParties = new String[]{"环保局", "城管局", "应急局", "盛庄街道"};


    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChartview, defStyleAttr, 0);
        title = typedArray.getString(R.styleable.PieChartview_pie_title);

        typedArray.recycle();
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_pie_chart, this, true);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        //初始化饼图
        initPieChart();
    }

    private void initPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0, 0, 0, -10);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setDrawCenterText(false);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setDrawEntryLabels(false);   // 不绘制x的值   x:罗庄,y:25%
        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);
        pieChart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(10f);
        l.setFormSize(7f);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(3f);
        l.setWordWrapEnabled(true);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
//        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setNoDataText("暂无数据");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.e("onValueSelected:", "" + e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.e("onNothingSelected:", "被点击了");
    }

    private void setData(List<GERPIndexBean.EventInfoBean> dataList) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < dataList.size(); i++) {
            GERPIndexBean.EventInfoBean item = dataList.get(i);
            entries.add(new PieEntry(item.getCount(),item.getPart_name()));
            colors.add(Color.parseColor(item.getColor()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);  // 不画值

        // add a lot of colors
        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void setPieData(List<GERPIndexBean.EventInfoBean> data){
        if (data!=null&&data.size()>0){
            for (GERPIndexBean.EventInfoBean datum : data) {
                if (datum.getCount()!=0){
                    setData(data);
                    tvDodata.setVisibility(View.GONE);
                    return;
                }
            }
            tvDodata.setVisibility(View.VISIBLE);
        }else{
            pieChart.removeAllViews();
            pieChart.setData(null);
        }
    }

}
