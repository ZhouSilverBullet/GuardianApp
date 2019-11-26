package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;

/**
 * @author :  lwb
 * Date: 2019/5/22
 * Desc:
 */
public class EventSearchTitleAdapter extends BaseQuickAdapter<ShowPartBean.KeywordInfoBean, BaseViewHolder> {

    public EventSearchTitleAdapter() {
        super(R.layout.item_text);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowPartBean.KeywordInfoBean item) {
        helper.setText(R.id.tv_text,item.classify_keyword);
    }

}
