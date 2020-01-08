package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.KqstDayBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQST_Adapter extends BaseQuickAdapter<KqstDayBean.SignLogBean, BaseViewHolder> {
    private static final String TAG = "KQST_Adapter";

    public KQST_Adapter() {
        super(R.layout.item_kqst_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, KqstDayBean.SignLogBean item) {
        TextView tvTopLine = helper.getView(R.id.tvTopLine);
        TextView tvLineVer = helper.getView(R.id.tvLineVer);
        TextView tvLine = helper.getView(R.id.tvLineXXXX);
        TextView tvStatus = helper.getView(R.id.tvStatus);
        TextView tvTime = helper.getView(R.id.tvTime);
        TextView tvtime_dk = helper.getView(R.id.tvtime_dk);
        TextView tvPlace = helper.getView(R.id.tvPlace);
        ImageView ivIcon = helper.getView(R.id.ivIcon);

        tvTopLine.setVisibility(helper.getPosition() == 0 ? View.VISIBLE : View.GONE);
        tvLineVer.setVisibility(helper.getPosition() == 0 ? View.GONE : View.VISIBLE);
        tvLine.setVisibility(helper.getLayoutPosition() == getData().size() - 1 ? View.INVISIBLE : View.VISIBLE);

        if (!TextUtils.isEmpty(item.sign_name) && !TextUtils.isEmpty(item.sys_date)) {
            tvTime.setText(item.sign_name + "时间" + item.sys_date);
            tvTime.setVisibility(View.VISIBLE);
        } else {
            tvTime.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(item.sign_time)) {
            tvtime_dk.setText("打卡时间" + getHMtime(item.sign_time));
            tvtime_dk.setVisibility(View.VISIBLE);
            tvStatus.setVisibility(View.VISIBLE);
        } else {
            tvtime_dk.setVisibility(View.INVISIBLE);
            tvStatus.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(item.address)) {
            tvPlace.setText(item.address);
            tvPlace.setVisibility(View.VISIBLE);
            ivIcon.setVisibility(View.VISIBLE);
        } else {
            ivIcon.setVisibility(View.INVISIBLE);
            tvPlace.setVisibility(View.INVISIBLE);
        }

        int color = Color.parseColor("#33CC00");
        String tx = "正常";
        int bg = R.drawable.shape_item_dk_normal_bg;
        switch (item.status) {
            case 1: //正常
                color = Color.parseColor("#33CC00");
                tx = "正常";
                bg = R.drawable.shape_item_dk_normal_bg;
                break;
            case 5:  //迟到
                color = Color.parseColor("#FFCC33");
                tx = "迟到";
                bg = R.drawable.shape_item_dk_cd_bg;
                break;
            case 6:  //早退
                color = Color.parseColor("#FFCC33");
                tx = "早退";
                bg = R.drawable.shape_item_dk_cd_bg;
                break;
            case 7:  //旷工
                color = Color.parseColor("#FF0000");
                tx = "旷工";
                bg = R.drawable.shape_item_dk_kg_bg;
                break;
        }
        tvStatus.setTextColor(color);
        tvStatus.setText(tx);
        tvStatus.setBackgroundResource(bg);
    }


    /**
     * 获取(时:分)
     *
     * @param time
     * @return
     */
    private String getHMtime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
            Date parse = format.parse(time);
            time = format2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
