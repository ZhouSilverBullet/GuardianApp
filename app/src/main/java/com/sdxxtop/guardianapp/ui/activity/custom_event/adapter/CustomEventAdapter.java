package com.sdxxtop.guardianapp.ui.activity.custom_event.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventStreamBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportActivity;
import com.sdxxtop.guardianapp.ui.activity.custom_event.CustomHeightBottomSheetDialog;

/**
 * @author :  lwb
 * Date: 2019/12/17
 * Desc:
 */
public class CustomEventAdapter extends BaseQuickAdapter<EventStreamBean.SerringsBean, BaseViewHolder> {

    private CustomHeightBottomSheetDialog mDialog;

    public CustomEventAdapter(CustomHeightBottomSheetDialog dialog) {
        super(R.layout.item_custom_event_layout);
        this.mDialog = dialog;
    }

    @Override
    protected void convert(BaseViewHolder helper, EventStreamBean.SerringsBean item) {
        helper.setText(R.id.tv_title, item.event_settings_name);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, CustomEventActivity.class);
                Intent intent = new Intent(mContext, EventReportActivity.class);
                intent.putExtra("streamId", item.event_settings_id);
                intent.putExtra("streamName", item.event_settings_name);
                mContext.startActivity(intent);
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
    }
}
