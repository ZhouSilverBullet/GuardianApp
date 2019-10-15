package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventListBean;
import com.sdxxtop.guardianapp.ui.activity.PartEventListActivity;

/**
 * @author :  lwb
 * Date: 2019/10/15
 * Desc:
 */
public class OrganizationAdapter extends BaseQuickAdapter<EventListBean.CompleteInfo, BaseViewHolder> {

    private String mStartTime, mEndTime;
    private int mStatus;

    public OrganizationAdapter() {
        super(R.layout.item_event_organization);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventListBean.CompleteInfo item) {
        helper.setText(R.id.part_name, item.getPart_name());
        helper.setText(R.id.event_num, "" + item.getCount());
        TextView tvDetail = helper.getView(R.id.tv_detail);
        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PartEventListActivity.class);
                intent.putExtra("part_name", item.getPart_name());
                intent.putExtra("part_id", String.valueOf(item.getPart_id()));
                intent.putExtra("status", mStatus);
                intent.putExtra("startTime", mStartTime);
                intent.putExtra("endTime", mEndTime);

                mContext.startActivity(intent);
            }
        });
    }

    public void setTime(String startTime, String endTime, int status) {
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mStatus = status;
    }
}
