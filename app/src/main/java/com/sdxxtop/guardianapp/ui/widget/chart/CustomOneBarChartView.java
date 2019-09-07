package com.sdxxtop.guardianapp.ui.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.sdxxtop.guardianapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class CustomOneBarChartView extends LinearLayout {

    @BindView(R.id.barchart)
    BarChart mChart;

    private XAxis xAxis;
    private YAxis leftAxis;
    private Legend legend;
    public static final int heightColor = Color.rgb(37, 147, 231); //Color.parseColor("#FF2593E7"); //Color.rgb(37,147,231)
    public static final String[] txStr = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月",};

    public CustomOneBarChartView(Context context) {
        this(context, null);
    }

    public CustomOneBarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomOneBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_barchart_data, this, true);
        ButterKnife.bind(this);
        initBarChart(mChart);
//        initData(null);
    }

    public void initData(List<Float> data, int color) {
        //处理数据是 记得判断每条柱状图对应的数据集合 长度是否一致
        List<List<Float>> chartDataMap = new ArrayList<>();
        List<Float> yValue1 = new ArrayList<>();
        List<Integer> colors = Arrays.asList(
                getResources().getColor(R.color.color_C490BF), getResources().getColor(R.color.color_00B7EE)
        );
        showBarChart(Arrays.asList(txStr), data, color);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleNum(data.size()), 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        mChart.getViewPortHandler().refresh(matrix, mChart, false);//将图表动画显示之前进行缩放
        mChart.animateX(1000);// 立即执行的动画,x轴
        setLimitLine(50);
    }

    private String getTime(String add_time) {
        String result = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            Date parse = format.parse(add_time);
            result = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 初始化BarChart图表
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);

        barChart.setDoubleTapToZoomEnabled(false);
        //禁止拖拽
        barChart.setDragEnabled(true);
        //X轴或Y轴禁止缩放
        barChart.setScaleXEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.setScaleEnabled(false);
        //禁止所有事件
//        barChart.setTouchEnabled(false);


        //不显示边框
        barChart.setDrawBorders(false);

        //不显示右下角描述内容
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //设置动画效果
        barChart.animateY(1000, Easing.Linear);
        barChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(300);
        leftAxis = barChart.getAxisLeft();
        barChart.getAxisRight().setEnabled(false);


//        xAxis.setAxisMinimum(0f);
        //保证Y轴从0开始，不然会上移一点
//        leftAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMinimum(0f);

        //不绘制X Y轴线条
        xAxis.setDrawAxisLine(false);   // 不绘制 x 轴底部线
        leftAxis.setDrawAxisLine(true);
        //不显示左侧Y轴
        leftAxis.setEnabled(false);

        //不显示X轴网格线
        xAxis.setDrawGridLines(false);
        leftAxis.setDrawGridLines(false);
        //右侧Y轴网格线设置为虚线
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(false);

        mChart.setNoDataText("暂无数据");
    }


    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
//        barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }

    //30个横坐标时，缩放4f是正好的。
    private float scalePercent = 3f / 30f;

    private float scaleNum(int xCount) {
        return xCount * scalePercent;
    }

    /**
     * @param xValues   X轴的值
     * @param dataLists LinkedHashMap<String, List<Float>>
     *                  key对应柱状图名字  List<Float> 对应每类柱状图的Y值
     * @param colors
     */
    public void showBarChart(final List<String> xValues, List<Float> dataLists, @ColorRes int colors) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dataLists.size(); i++) {
            /**
             *  如果需要添加TAG标志 可使用以下构造方法
             *  BarEntry(float x, float y, Object data)
             *  e.getData()
             */
            entries.add(new BarEntry(i, dataLists.get(i)));
        }
        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, "");
        initBarDataSet(barDataSet, colors);
        barDataSet.setHighLightColor(heightColor);

        //X轴自定义值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) Math.abs(value) % xValues.size());
            }
        });
        //右侧Y轴自定义值
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) + "";
            }
        };
        leftAxis.setValueFormatter(formatter);

        BarData data = new BarData(barDataSet);
        data.setHighlightEnabled(true);
        mChart.setData(data);

        XYMarkerView mv = new XYMarkerView(this.getContext(), formatter);
        mv.setChartView(mChart);
        mChart.setMarker(mv);
    }

    public void setLimitLine(int height) {
        LimitLine limitLine = new LimitLine(height, "");
        limitLine.setLineWidth(1f);
        limitLine.setLineColor(Color.parseColor("#999999"));
        limitLine.enableDashedLine(20f, 8f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);
        leftAxis.addLimitLine(limitLine);
    }

    public void setNoData() {
        if (mChart != null) {
            mChart.removeAllViews();
            mChart.setData(null);
        }
    }
}
