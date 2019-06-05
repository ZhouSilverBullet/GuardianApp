package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;
import android.widget.TextView;

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
public class PaifaAdapter extends BaseQuickAdapter<EventReadIndexBean.ExtraDateBean,BaseViewHolder> {

    public PaifaAdapter(int layoutResId, @Nullable List<EventReadIndexBean.ExtraDateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReadIndexBean.ExtraDateBean item) {
//        StringBuilder append = new StringBuilder().append("派发时间：").append(handleTime(item.getSend_time()));
//        helper.setText(R.id.tv_end_time,"截止日期：" + handleShortTime(item.getOperate_date()));
        if (helper.getAdapterPosition()>0){
            helper.setGone(R.id.v_line,false);
        }
        StringBuilder append = new StringBuilder().append("派发时间：").append(item.getSend_time());
        helper.setText(R.id.tv_end_time,"截止解决日期：" + item.getOperate_date());
        helper.setText(R.id.tv_distributed_time,append.toString());
        showPaifaLayout(item.getImportant_type(),helper.getView(R.id.tv_end_point));
    }

    public void showPaifaLayout(int type, TextView tvEndPoint) {
        switch (type) { //事件重要性(1:低 2:中 3:高)
            case 1:
                tvEndPoint.setText("事件重要性：低");
                break;
            case 2:
                tvEndPoint.setText("事件重要性：中");
                break;
            default:
                tvEndPoint.setText("事件重要性：高");
                break;
        }
    }

    private String handleTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
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
