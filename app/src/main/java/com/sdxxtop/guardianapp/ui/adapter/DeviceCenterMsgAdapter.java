package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.ui.activity.DeviceWarnDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/9
 * Desc:
 */
public class DeviceCenterMsgAdapter extends BaseQuickAdapter<UnreadNewslistBean.EventItemBean, BaseViewHolder> {
    private int mType;

    public DeviceCenterMsgAdapter(int layoutResId, @Nullable List<UnreadNewslistBean.EventItemBean> data, int type) {
        super(layoutResId, data);
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadNewslistBean.EventItemBean item) {
        helper.setText(R.id.tv_device_name, item.getTitle());
        helper.setText(R.id.tv_exception, item.getContent());
        helper.setText(R.id.tv_time, getFormatTime(item.getUpdate_time()));

        helper.setText(R.id.tv_icon, mType == 4 ? "警" : "评");
        helper.setBackgroundRes(R.id.tv_icon, mType == 4 ? R.drawable.shape_red_bg : R.drawable.shape_yellow_bg);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (mType == 4) {
                    intent =new Intent(mContext, DeviceWarnDetailActivity.class);
                    intent.putExtra("early_id", item.getEarly_id());
                    mContext.startActivity(intent);
                }else{
                    intent =new Intent(mContext, EventReportDetailActivity.class);
                    intent.putExtra("eventId", String.valueOf(item.getEvent_id()));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    public String getFormatTime(String time) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatResult = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date parse = format.parse(time);
            result = formatResult.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
