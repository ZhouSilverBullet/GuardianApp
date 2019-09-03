package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.FlyEventListBean;
import com.sdxxtop.guardianapp.ui.activity.EventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyEventDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/8/30
 * Desc:
 */
public class FlyDataAdapter extends BaseQuickAdapter<FlyEventListBean.DayTash, BaseViewHolder> {

    public FlyDataAdapter(int layoutResId, @Nullable List<FlyEventListBean.DayTash> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FlyEventListBean.DayTash item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_part, item.part_name);
        helper.setText(R.id.tv_time, getTime(item.add_time));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.upload_type == 1 || item.upload_type == 0) {
                    Intent intent = new Intent(mContext, FlyEventDetailActivity.class);
                    intent.putExtra("type",item.upload_type);
                    intent.putExtra("eventId",item.id);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, EventDetailActivity.class);
                    intent.putExtra("type",item.upload_type);
                    intent.putExtra("eventId",item.id);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private String getTime(String addTime) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");

        try {
            Date parse = format.parse(addTime);
            result = format2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
