package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.PartEventListBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;

/**
 * @author :  lwb
 * Date: 2019/5/9
 * Desc:
 */
public class PartEventListAdapter extends BaseQuickAdapter<PartEventListBean.ClData, BaseViewHolder> {


    public PartEventListAdapter() {
        super(R.layout.item_part_event);
    }

    @Override
    protected void convert(BaseViewHolder helper, PartEventListBean.ClData item) {
        if (item==null)return;
        helper.setText(R.id.tv_event_status, getStatus(item.getStatus()));
        helper.setText(R.id.tv_part_name, item.getTitle());
        helper.setText(R.id.tv_part_event_num, item.getAdd_time());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventReportDetailActivity_new.class);
                intent.putExtra("eventId", String.valueOf(item.getEvent_id()));
                mContext.startActivity(intent);
            }
        });
    }

    // 1 待处理  2、处理中  3待验收  4是已完成
    public String getStatus(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "待处理";
                break;
            case 2:
                str = "处理中";
                break;
            case 3:
                str = "待验收";
                break;
            case 4:
                str = "已完成";
                break;
        }
        return str;
    }
}
