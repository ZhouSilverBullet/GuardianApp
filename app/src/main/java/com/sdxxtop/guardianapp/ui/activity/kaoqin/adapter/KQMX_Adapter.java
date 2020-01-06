package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQMX_Adapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public KQMX_Adapter() {
        super(R.layout.item_kqmx_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_attendance_detail_date, item);
    }
}
