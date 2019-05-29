package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/28
 * Desc:
 */
public class EventDiscretionListAdapter extends BaseQuickAdapter<EventDiscretionListBean.PartolBean, BaseViewHolder> {
    public EventDiscretionListAdapter(int layoutResId, @Nullable List<EventDiscretionListBean.PartolBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventDiscretionListBean.PartolBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_time,"整改时限："+item.getRectify_date());
        helper.setText(R.id.tv_address,"提交时间："+item.getRectify_date());
        helper.setText(R.id.tv_status,item.getStatus()==1?"已完成":"待复查");

    }
}
