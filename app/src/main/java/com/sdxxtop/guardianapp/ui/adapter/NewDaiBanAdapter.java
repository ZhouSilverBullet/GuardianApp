package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.WorkIndexBean;

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
        helper.setText(R.id.tv_title, "待解决: " + item.title);
        helper.setText(R.id.tv_date, item.end_date);
        helper.setText(R.id.tv_time, item.day+"天");
    }
}
