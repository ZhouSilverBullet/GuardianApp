package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DeviceListBean;
import com.sdxxtop.guardianapp.ui.activity.DeviceDataDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/9
 * Desc:
 */
public class DeviceListAdapter extends BaseQuickAdapter<DeviceListBean.DeviceInfoBean, BaseViewHolder> {

    public DeviceListAdapter(int layoutResId, @Nullable List<DeviceListBean.DeviceInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListBean.DeviceInfoBean item) {
        LinearLayout ll_exception_layout = helper.getView(R.id.ll_exception_layout);
        LinearLayout ll_normal_layout = helper.getView(R.id.ll_normal_layout);
        ll_exception_layout.setVisibility(item.getStatus() == 1 ? View.GONE : View.VISIBLE);
        ll_normal_layout.setVisibility(item.getStatus() == 1 ? View.VISIBLE : View.GONE);

        switch (item.getStatus()) {
            case 1:
                helper.setTextColor(R.id.tv_data_normal_pm1, Color.parseColor("#1296DB"));
                helper.setTextColor(R.id.tv_data_normal_pm2, Color.parseColor("#1296DB"));
                helper.setImageResource(R.id.iv_icon, R.drawable.icon_data_normal);
                break;
            case 2:
                helper.setText(R.id.tv_exception, "预警时数据");
                helper.setTextColor(R.id.tv_current_pm1, Color.parseColor("#E99E4C"));
                helper.setTextColor(R.id.tv_current_pm2, Color.parseColor("#E99E4C"));
                helper.setTextColor(R.id.tv_exception_pm1, Color.parseColor("#E99E4C"));
                helper.setTextColor(R.id.tv_exception_pm2, Color.parseColor("#E99E4C"));
                helper.setImageResource(R.id.iv_icon, R.drawable.icon_data_exception);
                break;
            case 3:
                helper.setText(R.id.tv_exception, "设备异常");
                helper.setTextColor(R.id.tv_current_pm1, Color.parseColor("#FF0101"));
                helper.setTextColor(R.id.tv_current_pm2, Color.parseColor("#FF0101"));
                helper.setTextColor(R.id.tv_exception_pm1, Color.parseColor("#FF0101"));
                helper.setTextColor(R.id.tv_exception_pm2, Color.parseColor("#FF0101"));
                helper.setImageResource(R.id.iv_icon, R.drawable.icon_device_exception);
                break;
        }
        helper.setText(R.id.tv_title, item.getDevice_name());
        if (item.getStatus() != 1) {
            helper.setText(R.id.tv_exception_pm1, item.getTpfpm() + "");
            helper.setText(R.id.tv_exception_pm2, item.getTenpm() + "");
            helper.setText(R.id.tv_current_pm1, item.getReal_tpfpm() + "");
            helper.setText(R.id.tv_current_pm2, item.getReal_tenpm() + "");
        } else {
            helper.setText(R.id.tv_data_normal_pm1, item.getReal_tpfpm() + "");
            helper.setText(R.id.tv_data_normal_pm2, item.getReal_tenpm() + "");
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DeviceDataDetailActivity.class);
                intent.putExtra("deviceId", String.valueOf(item.getDevice_id()));
                mContext.startActivity(intent);
            }
        });
    }
}
