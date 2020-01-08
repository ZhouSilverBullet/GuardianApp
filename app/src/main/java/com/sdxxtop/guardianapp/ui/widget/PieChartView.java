package com.sdxxtop.guardianapp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.model.bean.RecordIndexBean;
import com.sdxxtop.guardianapp.utils.UIUtils;

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
    @BindView(R.id.tv_up)
    public TextView tv_up;

    private String title;

    protected String[] mParties = new String[]{"环保局", "城管局", "应急局", "盛庄街道"};
    private boolean prevIsShow;
    private boolean prevIsBig;
    private boolean isDrawValue = true;

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
        prevIsShow = typedArray.getBoolean(R.styleable.PieChartview_tv_prev_is_show, false);
        prevIsBig = typedArray.getBoolean(R.styleable.PieChartview_is_big, false);

        typedArray.recycle();
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(prevIsBig ? R.layout.view_pie_chart : R.layout.view_pie_chart2, this, true);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (prevIsShow) {
            tv_up.setVisibility(View.VISIBLE);
        } else {
            tv_up.setVisibility(View.GONE);
        }
        //初始化饼图
        initPieChart();
    }

    private void initPieChart() {
        pieChart.setUsePercentValues(false);  // 不显示百分比
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10.0f, 10.0f, 10.0f, 10.0f);

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
        pieChart.setRotationEnabled(true);
//        pieChart.setHighlightPerTapEnabled(false);

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
        Log.e("onValueSelected:", "" + e.toString() + "data==" + e.getData());
        try {
            String data = (String) e.getData();
            if (!"".equals(data)) {
                String[] split = data.split(",");
                if (mListener != null) {
                    mListener.pieItemClick(split[1]);
                }
            }
        } catch (Exception e1) {
            UIUtils.showToast("该部门下无子部门");
        }
    }

    @Override
    public void onNothingSelected() {
        Log.e("onNothingSelected:", "被点击了");
    }

    private void setData(List<EventChartBean.ChartInfoBean> dataList) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < dataList.size(); i++) {
            EventChartBean.ChartInfoBean item = dataList.get(i);
            if (item.getCount() != 0) {
                entries.add(new PieEntry(item.getNum(), item.getPart_name(), item.getCount() + "," + item.getPart_id()));
                colors.add(Color.parseColor(item.getColor()));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(isDrawValue);  // 不画值
        // add a lot of colors
        dataSet.setColors(colors);
        dataSet.setUsingSliceColorAsValueLineColor(true);
        dataSet.setValueLinePart1OffsetPercentage(85.0f);
        dataSet.setValueLinePart1Length(1.2f);
        dataSet.setValueLinePart2Length(0.7f);
        dataSet.setHighlightEnabled(true);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//标签显示在外面，关闭显示在饼图里面
//        dataSet.setValueLineColor(0xff000000);  //设置指示线条颜色,必须设置成这样，才能和饼图区域颜色一致
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyPercentFormatter());
        data.setValueTextColors(colors);
        data.setValueTextSize(12f);
        data.setHighlightEnabled(true);

        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    public void setPieData(List<EventChartBean.ChartInfoBean> data) {
        if (data != null && data.size() > 0) {
            for (EventChartBean.ChartInfoBean datum : data) {
                if (datum.getCount() != 0) {
                    setData(data);
                    if (!prevIsBig) {
                        findViewById(R.id.ll_layout).setVisibility(View.VISIBLE);
                    }
                    tvDodata.setVisibility(View.GONE);
                    return;
                }
            }
            tvDodata.setVisibility(View.VISIBLE);
            if (!prevIsBig) {
                findViewById(R.id.ll_layout).setVisibility(View.INVISIBLE);
            }
        } else {
            pieChart.removeAllViews();
            pieChart.setData(null);
            if (!prevIsBig) {
                findViewById(R.id.ll_layout).setVisibility(View.GONE);
            }
        }
    }

    class MyPercentFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            String text = "";
            if (entry != null) {
                String data = (String) entry.getData();
                String[] split = data.split(",");
                text = split[0];
            }
            return text;
//            return String.valueOf((int) value);
        }
    }

    private OnPieChartClick mListener;

    public void setOnPieChartClick(OnPieChartClick listener) {
        this.mListener = listener;
    }

    public interface OnPieChartClick {
        void pieItemClick(String id);
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    /**
     * 是否显示数据值
     *
     * @param isDrawValue
     */
    public void isDrawValue(boolean isDrawValue) {
        this.isDrawValue = isDrawValue;
    }

    /**
     * 设置自定义的指示值
     */
    public void setDescValue(RecordIndexBean.ListsBean data){
        if (!prevIsBig) {
            TextView tvRCKQ = findViewById(R.id.tvRCKQ);
            TextView tvXCZF = findViewById(R.id.tvXCZF);
            TextView tvQTBF = findViewById(R.id.tvQTBF);
            TextView tvWTSB = findViewById(R.id.tvWTSB);
            tvRCKQ.setText("日常考勤 " + data.usually_score);
            tvXCZF.setText("巡查走访 " + data.patrol_score);
            tvQTBF.setText("其他部分 " + data.other_score);
            tvWTSB.setText("问题上报 " + data.matter_score);
            pieChart.setCenterText(generateCenterSpannableText((int) data.score));
        }

    }

    public static SpannableString generateCenterSpannableText(int score) {
        SpannableString s = new SpannableString(""+score+"\n绩效总分");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 2, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 2, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 2, s.length(), 0);
        return s;
    }
}
