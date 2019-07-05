package com.sdxxtop.guardianapp.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DeviceDataBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/5
 * Desc:
 */
public class DeviceDataListAdapter extends BaseQuickAdapter<DeviceDataBean.DustDataBean, BaseViewHolder> {

    public DeviceDataListAdapter(int layoutResId, @Nullable List<DeviceDataBean.DustDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceDataBean.DustDataBean item) {
        helper.setText(R.id.tv_num, (helper.getLayoutPosition()+1) + "");
        helper.setText(R.id.tv_time, item.getAdd_time());
        helper.setText(R.id.tv_pm1, (int) item.getTpfpm() + "");
        helper.setText(R.id.tv_pm2, (int) item.getTenpm() + "");
        if (item.getTpfpm()>50){
            helper.setTextColor(R.id.tv_pm1, Color.parseColor("#FF0000"));
        }
        if (item.getTenpm()>50){
            helper.setTextColor(R.id.tv_pm2, Color.parseColor("#FF0000"));
        }
    }
}
