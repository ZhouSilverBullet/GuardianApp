package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.GERPresenter;
import com.sdxxtop.guardianapp.presenter.contract.GERContract;
import com.sdxxtop.guardianapp.ui.widget.PieChartView;

import butterknife.BindView;

public class GrantEventReportActivity extends BaseMvpActivity<GERPresenter> implements GERContract.IView {


    String[] status = new String[]{"已上报", "待处理", "处理中", "已处理", "已完成"};
    String[] enevtNum = new String[]{"890", "22", "007", "100", "800"};

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
        for (int i = 0; i < 5; i++) {
            //LinearLayout默认是水平(0)居中，现在改为垂直居中
            //实例化一个LinearLayout
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //设置LinearLayout属性(宽和高)
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            //设置边距
            layoutParams.setMargins(5, 10, 5, 10);
            //将以上的属性赋给LinearLayout
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setTag(i + 1);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.white));

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        int id = (int) v.getTag();
                        skipStatisticsById(id);
                    }
                }
            });

            //实例化一个TextView
            TextView tv = new TextView(this);
            //设置宽高以及权重
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvParams.setMargins(0, 10, 0, 0);
            //设置textview垂直居中
            tvParams.gravity = Gravity.CENTER;
            tv.setLayoutParams(tvParams);
            tv.setTextSize(18);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setText(enevtNum[i]);
            TextPaint paint = tv.getPaint();
            paint.setFakeBoldText(true);
            linearLayout.addView(tv);

            //实例化一个TextView
            TextView tv_status = new TextView(this);
            //设置宽高以及权重
            LinearLayout.LayoutParams tvParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvParams2.setMargins(0, 0, 0, 10);
            //设置textview垂直居中
            tvParams2.gravity = Gravity.CENTER;
            tv_status.setLayoutParams(tvParams2);
            tv_status.setTextSize(14);
            tv_status.setTextColor(getResources().getColor(R.color.black));
            tv_status.setText(status[i]);
            linearLayout.addView(tv_status);

            llLayout.addView(linearLayout);
        }
    }

    /**
     * 跳转activity
     *
     * @param id 跳转类型
     */
    private void skipStatisticsById(int id) {
        Intent intent = new Intent(this, EventStatistyActivity.class);
        intent.putExtra("eventId", id);
        startActivity(intent);
    }

    @Override
    public void showError(String error) {

    }
}
