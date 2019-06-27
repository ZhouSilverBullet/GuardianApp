package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventDiscretionListBean;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/28
 * Desc:
 */
public class EventDiscretionListAdapter extends BaseQuickAdapter<EventDiscretionListBean.PartolBean, BaseViewHolder> {
    public EventDiscretionListAdapter(int layoutResId, @Nullable List<EventDiscretionListBean.PartolBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventDiscretionListBean.PartolBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_time,"整改时限："+item.getRectify_date());
        helper.setText(R.id.tv_address,"提交时间："+item.getAdd_date());
        helper.setText(R.id.tv_status,item.getStatus()==1?"待复查":"已完成");

        helper.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PatrolAddDetailActivity.class);
                intent.putExtra("patrol_id",item.getPatrol_id());
                mContext.startActivity(intent);
            }
        });
    }
}
