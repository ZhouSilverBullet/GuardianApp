package com.sdxxtop.guardianapp.ui.activity.custom_event;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventStreamBean;

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
                Intent intent = new Intent(mContext, CustomEventActivity.class);
                intent.putExtra("streamId", item.event_settings_id);
                mContext.startActivity(intent);
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
    }
}
