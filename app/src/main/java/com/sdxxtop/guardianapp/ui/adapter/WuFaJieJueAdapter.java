package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventReadIndexBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class WuFaJieJueAdapter extends BaseQuickAdapter<EventReadIndexBean.ExtraBean,BaseViewHolder> {

    public WuFaJieJueAdapter(int layoutResId, @Nullable List<EventReadIndexBean.ExtraBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReadIndexBean.ExtraBean item) {

        if (helper.getAdapterPosition()==0){
            helper.setGone(R.id.v_line,false);
        }
        if (helper.getAdapterPosition()>0){
            helper.setGone(R.id.v_line,false);
        }
        helper.setText(R.id.chuli_time,"处理时间："+handleShortTime(item.getOperate_time()));
        helper.setText(R.id.chuli_result,"无法解决原因："+item.getExtra());
    }

    private String handleShortTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
