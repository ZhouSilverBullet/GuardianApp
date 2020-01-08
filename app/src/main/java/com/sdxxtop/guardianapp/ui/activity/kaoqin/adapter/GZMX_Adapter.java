package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GzmxDateBean;
import com.sdxxtop.guardianapp.utils.Date2Util;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class GZMX_Adapter extends BaseQuickAdapter<GzmxDateBean.EventBean, BaseViewHolder> {
    public GZMX_Adapter() {
        super(R.layout.item_gzmx_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, GzmxDateBean.EventBean item) {
        helper.setText(R.id.tvDate, Date2Util.getYMDTime(item.add_time));
        helper.setText(R.id.tvTime, Date2Util.getHMTime(item.add_time));
        helper.setText(R.id.tvTitle, item.title);
        helper.setText(R.id.tvStatus, getStatusStr(item.status));
        helper.setText(R.id.tvCategory, item.category_name);
        helper.setText(R.id.tvScore, item.score);
    }

    //1：待派发 2：待解决 3：待验收 4：已完成 5：验收未通过 6：驳回 7：删除）
    public String getStatusStr(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "待派发";
                break;
            case 2:
                str = "待解决";
                break;
            case 3:
                str = "待验收";
                break;
            case 4:
                str = "已完成";
                break;
            case 5:
                str = "验收未通过";
                break;
            case 6:
                str = "驳回";
                break;
            case 7:
                str = "删除";
                break;
        }
        return str;
    }
}
