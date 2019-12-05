package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.SectionEventBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/26
 * Desc:
 */
public class SectionEventAdapter extends BaseQuickAdapter<SectionEventBean.ClaimInfoBean, BaseViewHolder> {
    public SectionEventAdapter(int layoutResId, @Nullable List<SectionEventBean.ClaimInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionEventBean.ClaimInfoBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, getTimeStr(item.getAdd_time()));
        helper.setText(R.id.tv_place, item.getPlace());
        helper.setText(R.id.tv_status, getStatusStr(item.getStatus()));
        helper.setText(R.id.tv_renlingren, TextUtils.isEmpty(item.getName()) ? "" : "认领人：" + item.getName());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventReportDetailActivity_new.startDetailActivity(v.getContext(), String.valueOf(item.getEvent_id()));
            }
        });
    }

    public String getStatusStr(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "待认领";
                break;
            case 2:
                str = "已认领";
                break;
            case 3:
                str = "待评价";
                break;
            case 4:
                str = "已完成";
                break;
        }
        return str;
    }

    public String getTimeStr(String date) {
        String time = "上报时间：";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatResult = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parse = format.parse(date);
            time += formatResult.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
