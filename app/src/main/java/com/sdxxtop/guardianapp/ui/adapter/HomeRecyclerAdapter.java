package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.activity.EnvironmentalTestActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.ImpeachReportActivity;
import com.sdxxtop.guardianapp.ui.activity.IntelligentSensorActivity;
import com.sdxxtop.guardianapp.ui.activity.StatisticsAnalyzeActivity;
import com.sdxxtop.guardianapp.ui.activity.TaskAgentsActivity;

import java.util.List;

import androidx.annotation.Nullable;

public class HomeRecyclerAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public HomeRecyclerAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        ImageView itemImageView = helper.getView(R.id.iv_right);
        itemImageView.setImageResource(item);

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToast("" + item);
                Intent intent = null;
                switch (item) {
                    case R.drawable.icon_6_home: // 待办任务
                        intent = new Intent(mContext, TaskAgentsActivity.class);
                        break;
                    case R.drawable.list_2:  //事件上报
                        intent = new Intent(mContext, EventReportActivity.class);
                        break;
                    case R.drawable.list_3:  // 环保检测
                        intent = new Intent(mContext, EnvironmentalTestActivity.class);
                        break;
                    case R.drawable.list_4:  // 智能传感器
                        intent = new Intent(mContext, IntelligentSensorActivity.class);
                        break;
                    case R.drawable.list_5:  // 统计分析
                        intent = new Intent(mContext, StatisticsAnalyzeActivity.class);
                        break;
                    case R.drawable.list_6:  // 检举举报
                        intent = new Intent(mContext, ImpeachReportActivity.class);
                        break;
                }

                if (intent != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
