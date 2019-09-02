package com.sdxxtop.guardianapp.ui.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;

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
public class CustomLineChartView extends LinearLayout {

    @BindView(R.id.linechart)
    LineChart mChart;

    private XAxis xAxis;
    private YAxis leftAxis;
    private Legend legend;

    public CustomLineChartView(Context context) {
        this(context, null);
    }

    public CustomLineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_linechart_data, this, true);
        ButterKnife.bind(this);
        initLineChart(mChart);
    }

    public void initData(List<DeviceDataBean.DustDataBean> data) {
        //处理数据是 记得判断每条折线图对应的数据集合 长度是否一致
        List<List<Float>> chartDataMap = new ArrayList<>();
        List<String> xValues = new ArrayList<>();
        List<Float> yValue1 = new ArrayList<>();
        List<Float> yValue2 = new ArrayList<>();
        List<Integer> colors = Arrays.asList(
                getResources().getColor(R.color.color_C490BF), getResources().getColor(R.color.color_00B7EE)
        );

        for (int i = 0; i < data.size(); i++) {
            DeviceDataBean.DustDataBean bean = data.get(i);
            yValue1.add(bean.getTpfpm());
            yValue2.add(bean.getTenpm());
            xValues.add(getTime(bean.getAdd_time()));
        }

        chartDataMap.add(yValue1);
        chartDataMap.add(yValue2);
        showLineChart(xValues, chartDataMap, colors);

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
    private void initLineChart(LineChart lineChart) {
        /***图表设置***/
        //背景颜色
        lineChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        lineChart.setDrawGridBackground(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        //禁止拖拽
        lineChart.setDragEnabled(true);
        //X轴或Y轴禁止缩放
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setScaleEnabled(false);
        //禁止所有事件
//        lineChart.setTouchEnabled(false);


        //不显示边框
        lineChart.setDrawBorders(false);

        //不显示右下角描述内容
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);

        //设置动画效果
        lineChart.animateY(1000, Easing.Linear);
        lineChart.animateX(1000, Easing.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(300);
        leftAxis = lineChart.getAxisLeft();
        lineChart.getAxisRight().setEnabled(false);


        //保证Y轴从0开始，不然会上移一点
//        xAxis.setAxisMinimum(0f);

        //不绘制X Y轴线条
        xAxis.setDrawAxisLine(true);
        leftAxis.setDrawAxisLine(true);
        //不显示左侧Y轴
        leftAxis.setEnabled(true);

        //不显示X轴网格线
        xAxis.setDrawGridLines(false);
        leftAxis.setDrawGridLines(false);
        //右侧Y轴网格线设置为虚线
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
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
     * 折线图始化设置 一个BarDataSet 代表一列折线图
     *
     * @param lineDataSet 折线图
     * @param color       折线图颜色
     */
    private void initBarDataSet(LineDataSet lineDataSet, int color) {
        lineDataSet.setColor(color);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //不显示折线图顶部值
        lineDataSet.setDrawValues(false);
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
     *                  key对应折线图名字  List<Float> 对应每类折线图的Y值
     * @param colors
     */
    public void showLineChart(final List<String> xValues, List<List<Float>> dataLists, @ColorRes List<Integer> colors) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < dataLists.size(); i++) {
            List<Float> yValueList = dataLists.get(i);
            List<Entry> entries = new ArrayList<>();
            for (int j = 0; j < yValueList.size(); j++) {
                /**
                 *  如果需要添加TAG标志 可使用以下构造方法
                 *  BarEntry(float x, float y, Object data)
                 *  e.getData()
                 */
                entries.add(new Entry(j, yValueList.get(j)));
            }
            // 每一个BarDataSet代表一类折线图
            LineDataSet lineDataSet = new LineDataSet(entries, "");
            initBarDataSet(lineDataSet, colors.get(i));
            dataSets.add(lineDataSet);
        }

        //X轴自定义值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) Math.abs(value) % xValues.size());
            }
        });
        //右侧Y轴自定义值
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) (value) + "";
            }
        });

        LineData data = new LineData(dataSets);

        /**
         * float groupSpace = 0.3f;   //折线图组之间的间距
         * float barSpace =  0.05f;  //每条折线图之间的间距  一组两个折线图
         * float barWidth = 0.3f;    //每条折线图的宽度     一组两个折线图
         * (barWidth + barSpace) * 2 + groupSpace = (0.3 + 0.05) * 2 + 0.3 = 1.00
         * 3个数值 加起来 必须等于 1 即100% 按照百分比来计算 组间距 折线图间距 折线图宽度
         */
//        int barAmount = dataLists.size(); //需要显示折线图的类别 数量
//        //设置组间距占比30% 每条折线图宽度占比 70% /barAmount  折线图间距占比 0%
//        float groupSpace = 0.3f; //折线图组之间的间距
//        float barWidth = (1f - groupSpace) / barAmount;
//        float barSpace = 0f;
        //设置折线图宽度
//        data.setBarWidth(barWidth);
        //(起始点、折线图组间距、折线图之间间距)
//        data.groupBars(0f, groupSpace, barSpace);
        data.setHighlightEnabled(false);
        mChart.setData(data);

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(xValues.size());
        //将X轴的值显示在中央
//        xAxis.setCenterAxisLabels(true);
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
