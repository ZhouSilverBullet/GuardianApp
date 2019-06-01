package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexBean;
import com.sdxxtop.guardianapp.ui.activity.CenterMessage2Activity;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class CenterMessageAdapter extends BaseQuickAdapter<UnreadIndexBean, BaseViewHolder> {


    private final String time;

    public CenterMessageAdapter(int layoutResId, @Nullable List<UnreadIndexBean> data) {
        super(layoutResId, data);
        CalendarDay day = new CalendarDay();
        time = ""+day.getYear()+"."+ Date2Util.getZeroTime(day.getMonth()+1)+"."+ Date2Util.getZeroTime(day.getDay());
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadIndexBean item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_desc,item.getTitle());
        helper.setText(R.id.tv_time,time);
        if(item.getCount()!=0){
            helper.setVisible(R.id.tv_messgae_count,true);
            helper.setText(R.id.tv_messgae_count,String.valueOf(item.getCount()));
        }else{
            helper.setVisible(R.id.tv_messgae_count,false);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CenterMessage2Activity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("type",item.getType());
                mContext.startActivity(intent);
            }
        });
    }
}
