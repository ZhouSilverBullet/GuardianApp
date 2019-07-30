package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class LiuzhuanAdapter extends BaseQuickAdapter<EventReadIndexBean.CirculationBean,BaseViewHolder> {

    public LiuzhuanAdapter(int layoutResId, @Nullable List<EventReadIndexBean.CirculationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReadIndexBean.CirculationBean item) {
        if (helper.getAdapterPosition()>0){
            helper.setGone(R.id.v_line,false);
        }
        StringBuilder append = new StringBuilder().append("流转时间：").append(Date2Util.handleTime(item.getAdd_time()));
        helper.setText(R.id.tv_liuzhuan_time,append.toString());
        helper.setText(R.id.tv_liuzhuan_section,"流转部门：" + item.getPart_name());
        helper.setText(R.id.tv_liuzhuan_result,"流转原因：" + item.getReason());

    }
}
