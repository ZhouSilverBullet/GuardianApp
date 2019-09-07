package com.sdxxtop.guardianapp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class NewDaiBanAdapter extends BaseQuickAdapter<WorkIndexBean.PendingEvent, BaseViewHolder> {
    public NewDaiBanAdapter(@Nullable List<WorkIndexBean.PendingEvent> data) {
        super(R.layout.item_new_event, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkIndexBean.PendingEvent item) {
        helper.setText(R.id.tv_date, item.end_date);
        helper.setText(R.id.tv_time, item.day + "天");

        String strStatus = "";
        switch (item.status) { //状态(1:待派发 2:待解决 3:待验收 4:验收通过 5:验收未通过)
            case 2:
                strStatus = item.is_claim == 1 ? "已认领" : "待解决";
                break;
            case 3:
                strStatus = item.is_claim == 1 ? "待评价" : "待验收";
                break;
            case 4:
                strStatus = item.is_claim == 1 ? "已完成" : "验收通过";
                break;
            case 5:
                strStatus = "验收未通过";
                break;
            default:
                strStatus = "待认领";
                break;
        }
        helper.setText(R.id.tv_title, strStatus + ": " + item.title);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventReportDetailActivity.startDetailActivity(v.getContext(), String.valueOf(item.event_id));
            }
        });
    }
}
