package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.ui.activity.EventDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.FlyEventDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/8/30
 * Desc:
 */
public class FlyDataAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FlyDataAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getAdapterPosition()/2==0){
                    mContext.startActivity(new Intent(mContext, FlyEventDetailActivity.class));
                }else{
                    mContext.startActivity(new Intent(mContext, EventDetailActivity.class));
                }
            }
        });
    }
}
