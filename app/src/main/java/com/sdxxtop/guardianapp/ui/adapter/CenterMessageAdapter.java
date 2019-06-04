package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexAdapterBean;
import com.sdxxtop.guardianapp.ui.activity.CenterMessage2Activity;
import com.sdxxtop.guardianapp.utils.Date2Util;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class CenterMessageAdapter extends BaseQuickAdapter<UnreadIndexAdapterBean, BaseViewHolder> {

    private int[] icons = new int[]{R.drawable.center_message_1, R.drawable.center_message_2, R.drawable.center_message_3};
    private final String time;

    public CenterMessageAdapter(int layoutResId, @Nullable List<UnreadIndexAdapterBean> data) {
        super(layoutResId, data);
        CalendarDay day = new CalendarDay();
        time = "" + day.getYear() + "." + Date2Util.getZeroTime(day.getMonth() + 1) + "." + Date2Util.getZeroTime(day.getDay());
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadIndexAdapterBean item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_desc, item.getTitle());
        helper.setText(R.id.tv_time, time);
        helper.setImageResource(R.id.iv_icon, icons[item.getType()-1]);
        setMsgUnread(item.getCount(),helper.getView(R.id.tv_messgae_count));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CenterMessage2Activity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("type", item.getType());
                mContext.startActivity(intent);
            }
        });
    }


    /**
     * 设置未读tab显示
     */
    public void setMsgUnread(long unRead, TextView redCountText) {
        if (unRead <= 0) {
            redCountText.setVisibility(View.INVISIBLE);
        } else {
            redCountText.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if (unRead < 10) {
                redCountText.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.point1));
            } else {
                if (unRead > 99) {
                    redCountText.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.point2));
                    unReadStr = "99+";
                }
            }
            if (unRead > 99) {
                unReadStr = "99+";
            }
            redCountText.setText(unReadStr);
        }
    }
}
