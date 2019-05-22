package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EnterpriseSecurityBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class SafeStaffDetailAdapter extends BaseQuickAdapter<EnterpriseSecurityBean.SignData, BaseViewHolder> {

    public SafeStaffDetailAdapter(int layoutResId, @Nullable List<EnterpriseSecurityBean.SignData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseSecurityBean.SignData item) {
        helper.setText(R.id.tv_time, item.getSign_time());
        helper.setText(R.id.tv_duration, String.valueOf(item.getDistance()));
        helper.setText(R.id.tv_distance, String.valueOf(item.getDuration()));
        helper.setGone(R.id.tv_sign_num, false);
    }
}
