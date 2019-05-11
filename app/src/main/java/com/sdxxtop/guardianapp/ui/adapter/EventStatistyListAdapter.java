package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.ui.activity.PartEventListActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/9
 * Desc:
 */
public class EventStatistyListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public EventStatistyListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PartEventListActivity.class);
                intent.putExtra("title", "环保局");
                mContext.startActivity(intent);
            }
        });
    }
}
