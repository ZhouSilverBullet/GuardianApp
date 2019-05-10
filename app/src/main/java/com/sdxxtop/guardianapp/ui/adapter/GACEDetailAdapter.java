package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class GACEDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public GACEDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SafeStaffDetailActivity.class);
                intent.putExtra("id",helper.getLayoutPosition());
                mContext.startActivity(intent);
            }
        });
    }
}
