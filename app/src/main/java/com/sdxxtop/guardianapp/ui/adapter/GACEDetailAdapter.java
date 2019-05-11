package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 *      巡逻详情和企业详情用同一个adapter  count : 字段 代表条目展示几个字段
 *
 */
public class GACEDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int count;

    public GACEDetailAdapter(int layoutResId, @Nullable List<String> data, int count) {
        super(layoutResId, data);
        this.count = count;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        if (count==1){
            helper.setGone(R.id.tv_check_num, false);
        }else if (count==2){
            helper.setGone(R.id.tv_check_num, true);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1) {  // 网格员
                    Intent intent = new Intent(mContext, SafeStaffDetailActivity.class);
                    intent.putExtra("id", helper.getLayoutPosition());
                    intent.putExtra("type", count);
                    mContext.startActivity(intent);
                } else if (count == 2) {  // 企业
                    Intent intent = new Intent(mContext, SafeStaffDetailActivity.class);
                    intent.putExtra("id", helper.getLayoutPosition());
                    intent.putExtra("type", count);
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
