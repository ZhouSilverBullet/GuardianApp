package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GridreportUserreportBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/16
 * Desc:
 */
public class GridreportUserreportAdapter extends BaseQuickAdapter<GridreportUserreportBean.ClData, BaseViewHolder> {

    public GridreportUserreportAdapter(int layoutResId, @Nullable List<GridreportUserreportBean.ClData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GridreportUserreportBean.ClData item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getAdd_time());
        helper.setText(R.id.tv_status, getStatus(item.getStatus()));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventReportDetailActivity.class);
                intent.putExtra("eventId", String.valueOf(item.getEvent_id()));
                mContext.startActivity(intent);
            }
        });
    }

    public String getStatus(int status) {
        //1、待处理事件 2、处理中事件 3、已处理事件 4、完成事件
        String str = "";
        switch (status) {
            case 1:
                str = "待处理";
                break;
            case 2:
                str = "处理中";
                break;
            case 3:
                str = "已处理";
                break;
            case 4:
                str = "已完成";
                break;
        }
        return str;
    }
}
