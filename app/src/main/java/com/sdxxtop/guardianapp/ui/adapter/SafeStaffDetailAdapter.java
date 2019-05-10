package com.sdxxtop.guardianapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class SafeStaffDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int type;
    public SafeStaffDetailAdapter(int layoutResId, @Nullable List<String> data ,int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (type==2){
            helper.setVisible(R.id.tv_sign_num,true);
        }
    }
}
