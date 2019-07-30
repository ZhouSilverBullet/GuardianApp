package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadIndexAdapterBean;
import com.sdxxtop.guardianapp.ui.activity.CenterMessage2Activity;
import com.sdxxtop.guardianapp.ui.activity.DeviceCenterMsgActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class CenterMessageAdapter extends BaseQuickAdapter<UnreadIndexAdapterBean, BaseViewHolder> {

    private int[] icons = new int[]{R.drawable.center_message_1, R.drawable.center_message_2, R.drawable.center_message_3};

    public CenterMessageAdapter(int layoutResId, @Nullable List<UnreadIndexAdapterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadIndexAdapterBean item) {
        helper.setText(R.id.tv_title, item.getName());
        helper.setText(R.id.tv_desc, item.getTitle());
        helper.setText(R.id.tv_time,handleMessageTime(item.getTime()));

        switch (item.getType()) {
            case 1:
                helper.setText(R.id.tv_status, "派");
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_green_bg);
                break;
            case 2:
                helper.setText(R.id.tv_status, "验");
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_blue_bg);
                break;
            case 3:
                helper.setText(R.id.tv_status, "整");
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_orange_bg);
                break;
            case 4:
                helper.setText(R.id.tv_status, "警");
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_red_bg);
                break;
            case 5:
                helper.setText(R.id.tv_status, "评");
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_yellow_bg);
                break;
        }

        setMsgUnread(item.getCount(), helper.getView(R.id.tv_messgae_count));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType()==4||item.getType()==5){
                    Intent intent = new Intent(mContext, DeviceCenterMsgActivity.class);
                    intent.putExtra("name", item.getName());
                    intent.putExtra("type", item.getType());
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, CenterMessage2Activity.class);
                    intent.putExtra("name", item.getName());
                    intent.putExtra("type", item.getType());
                    mContext.startActivity(intent);
                }
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

    public static String handleMessageTime(String time) {
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
}
