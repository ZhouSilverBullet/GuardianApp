package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/6/1
 * Desc:
 */
public class MessageInfoAdapter extends BaseQuickAdapter<UnreadNewslistBean.MessageInfoBean, BaseViewHolder> {

    private int mType;

    public MessageInfoAdapter(int layoutResId, @Nullable List<UnreadNewslistBean.MessageInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadNewslistBean.MessageInfoBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (mType==1){
            helper.setText(R.id.tv_time, item.getUpdate_time());
        }else{
            helper.setText(R.id.tv_time, item.getRectify_date());
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {     //1、返回的是事件数据  2、返回的是自行处理数据
                Intent intent = null;
                switch (mType) {
                    case 1:
                        intent = new Intent(mContext, EventReportDetailActivity.class);
                        intent.putExtra("eventId",String.valueOf(item.getEvent_id()));
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(mContext, PatrolAddDetailActivity.class);
                        intent.putExtra("patrol_id",item.getPatrol_id());
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
    }

    public void setType(int type) {
        this.mType = type;
    }
}
