package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/22
 * Desc:
 */
public class EventSearchTitleAdapter extends BaseQuickAdapter<EventSearchTitleBean.KeyInfo, BaseViewHolder> {

    public EventSearchTitleAdapter(int layoutResId, @Nullable List<EventSearchTitleBean.KeyInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventSearchTitleBean.KeyInfo item) {
        helper.setText(R.id.tv_text,item.getKeyword());
    }



}
