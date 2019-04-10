package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.MainIndexBean;
import com.sdxxtop.guardianapp.presenter.contract.PatrolContract;
import com.sdxxtop.guardianapp.ui.activity.EnvironmentalTestActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.ImpeachReportActivity;
import com.sdxxtop.guardianapp.ui.activity.IntelligentSensorActivity;
import com.sdxxtop.guardianapp.ui.activity.StatisticsAnalyzeActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class HomeRecyclerAdapter extends BaseQuickAdapter<MainIndexBean.EventBean, BaseViewHolder> {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HomeRecyclerAdapter(int layoutResId, @Nullable List<MainIndexBean.EventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainIndexBean.EventBean item) {
        ImageView itemImageView = helper.getView(R.id.iv_right);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTask1 = helper.getView(R.id.tv_new_task1);
        TextView tvTask2 = helper.getView(R.id.tv_new_task2);
        TextView tvScore1 = helper.getView(R.id.tv_score1);
        TextView tvScore2 = helper.getView(R.id.tv_score2);
        View view = helper.getView(R.id.ll_bg);
        List<EventData> eventDataList = new ArrayList<>();

        switch (item.type) {
            case MainIndexBean.EventBean.TYPE_PENDING:
                tvTitle.setText("代办任务");
                if (item.mPendingEventBean != null) {
                    for (MainIndexBean.PendingEventBean pendingEventBean : item.mPendingEventBean) {
                        EventData eventData = new EventData();
                        eventData.end_date = pendingEventBean.getEnd_date();
                        eventData.title = pendingEventBean.getTitle();
                        eventDataList.add(eventData);
                    }
                }
                handleTaskStatus(tvTask1, tvTask2, tvScore1, tvScore2, eventDataList);
                itemImageView.setImageResource(R.drawable.icon_1_list);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.color_7ECEF4));

                break;
            case MainIndexBean.EventBean.TYPE_ADD:
                tvTitle.setText("事件上报");

                if (item.mAddEventBean != null) {
                    for (MainIndexBean.AddEventBean pendingEventBean : item.mAddEventBean) {
                        EventData eventData = new EventData();
                        eventData.end_date = pendingEventBean.getEnd_date();
                        eventData.title = pendingEventBean.getTitle();
                        eventDataList.add(eventData);
                    }
                }
                handleTaskStatus(tvTask1, tvTask2, tvScore1, tvScore2, eventDataList);


                itemImageView.setImageResource(R.drawable.icon_2_list);
                view.setBackgroundColor(mContext.getResources().getColor(R.color.color_8C97CB));
                break;
        }


        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToast("" + item);
                Intent intent = null;
                switch (item.type) {
                    case MainIndexBean.EventBean.TYPE_PENDING: // 待办任务
                        intent = new Intent(mContext, TaskAgentsActivity.class);
                        break;
                    case MainIndexBean.EventBean.TYPE_ADD:  //事件上报
                        intent = new Intent(mContext, EventReportActivity.class);
                        break;
//                    case R.drawable.list_3:  // 环保检测
//                        intent = new Intent(mContext, EnvironmentalTestActivity.class);
//                        break;
//                    case R.drawable.list_4:  // 智能传感器
//                        intent = new Intent(mContext, IntelligentSensorActivity.class);
//                        break;
//                    case R.drawable.list_5:  // 统计分析
//                        intent = new Intent(mContext, StatisticsAnalyzeActivity.class);
//                        break;
//                    case R.drawable.list_6:  // 检举举报
//                        intent = new Intent(mContext, ImpeachReportActivity.class);
//                        break;
                }

                if (intent != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private void handleTaskStatus(TextView tvTask1, TextView tvTask2,
                                  TextView tvScore1, TextView tvScore2, List<EventData> eventDataList) {
        switch (eventDataList.size()) {
            case 0:
                tvTask1.setVisibility(View.INVISIBLE);
                tvTask2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tvTask1.setVisibility(View.VISIBLE);
                tvTask2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                tvTask1.setVisibility(View.VISIBLE);
                tvTask2.setVisibility(View.VISIBLE);
                break;
        }

        if (eventDataList.size() == 1) {
            tvTask1.setText(eventDataList.get(0).title);
            handleScore(tvScore1, eventDataList.get(0).end_date);
        } else if (eventDataList.size() >= 2) {
            tvTask1.setText(eventDataList.get(0).title);
            handleScore(tvScore1, eventDataList.get(0).end_date);

            tvTask2.setText(eventDataList.get(1).title);
            handleScore(tvScore2, eventDataList.get(1).end_date);
        }

    }

    public static class EventData {
        public String title;
        public String end_date;
    }


    public void handleScore(TextView tv, String strDate) {
        try {
            Date date = simpleDateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH) + 1;
            tv.setVisibility(View.VISIBLE);
            tv.setText(new StringBuilder().append("截止:").append(month).append(".").append(day));
        } catch (ParseException e) {
            e.printStackTrace();
            tv.setVisibility(View.INVISIBLE);
        }
    }
}
