package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.KqmxMonthBean;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQMX_Adapter extends BaseQuickAdapter<KqmxMonthBean.SignLogBean, BaseViewHolder> {
    String[] weekStr = {"一", "二", "三", "四", "五", "六", "日"};

    public KQMX_Adapter() {
        super(R.layout.item_kqmx_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, KqmxMonthBean.SignLogBean item) {
        if (item.week .equals("0") ) return;
        helper.setText(R.id.item_attendance_detail_date, item.date + " " + weekStr[Integer.parseInt(item.week) - 1]);
        helper.setText(R.id.tvClockTime, item.sign_log);
        helper.setText(R.id.tvOnlineTime, "" + item.time);
        helper.setText(R.id.item_attendance_detail_dec, "" + item.desc);
    }
}
