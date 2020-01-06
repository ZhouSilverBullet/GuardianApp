package com.sdxxtop.guardianapp.ui.activity.kaoqin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;
import com.sdxxtop.guardianapp.model.bean.EventChartBean;
import com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter.AssessDetailAdapter;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AssessDetailActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PieChartView pieChartView;

    @Override
    protected int getLayout() {
        return R.layout.activity_assess_detail;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AssessDetailAdapter adapter = new AssessDetailAdapter();
        recyclerView.setAdapter(adapter);

        List<AssessDetailAdapter.DetailBean> list = new ArrayList<>();
        list.add(new AssessDetailAdapter.DetailBean("迟到", "", "", "-0.2"));
        list.add(new AssessDetailAdapter.DetailBean("早退", "", "", "-0.3"));
        list.add(new AssessDetailAdapter.DetailBean("上报", "测试洛斯里克熟客舒克是", "其他类", "-0.9"));
        list.add(new AssessDetailAdapter.DetailBean("其他分类", "测试其他分类", "其他类", "-0.5"));
        list.add(new AssessDetailAdapter.DetailBean("在线时长", "", "", "-0.2"));
        list.add(new AssessDetailAdapter.DetailBean("巡逻距离", "", "", "-0.1"));
        list.add(new AssessDetailAdapter.DetailBean("事件验收", "测试事件验收", "验收类", "-0.1"));

        adapter.replaceData(list);

        pieChartView = new PieChartView(this);
        PieChart chart = pieChartView.getPieChart();
        initPieChart(chart);
        adapter.addHeaderView(pieChartView);
    }

    private void initPieChart(PieChart chart) {
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
        bean.setPart_name("");
        bean.setPart_id(2);
        data.add(bean);

        EventChartBean.ChartInfoBean bean2 = new EventChartBean.ChartInfoBean();
        bean2.setColor("#F39826");
        bean2.setCount(30);
        bean2.setNum(30);
        bean2.setPart_name("");
        bean2.setPart_id(1);
        data.add(bean2);

        pieChartView.setPieData(data);
    }

    public static SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("90\n绩效总分");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 2, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 2, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 2, s.length(), 0);
        return s;
    }
}
