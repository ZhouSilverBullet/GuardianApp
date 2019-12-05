package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.UnreadNewslistBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class CenterMessageListAdapter extends BaseQuickAdapter<UnreadNewslistBean.EventItemBean, BaseViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private int mType;
    private int isRenling;

    public CenterMessageListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnreadNewslistBean.EventItemBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (TextUtils.isEmpty(item.getUpdate_time())) {
            helper.setText(R.id.tv_time, item.getRectify_date());
        } else {
            helper.setText(R.id.tv_time, handleShortTime(item.getUpdate_time()));
        }
        TextView tvStatus = helper.getView(R.id.tv_status);
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        if (isRenling == 6) {
            tvStatus.setVisibility(View.VISIBLE);
            ivIcon.setVisibility(View.GONE);
        } else {
            tvStatus.setVisibility(View.GONE);
            ivIcon.setVisibility(View.VISIBLE);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 1) {
                    Intent intent = new Intent(mContext, EventReportDetailActivity_new.class);
                    intent.putExtra("eventId", String.valueOf(item.getEvent_id()));
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, PatrolAddDetailActivity.class);
                    intent.putExtra("patrol_id", item.getPatrol_id());
                    mContext.startActivity(intent);
                }
            }
        });
    }


    @Override
    public long getHeaderId(int position) {
        String classify = getData().get(position).getClassify();
        switch (classify) {
            case "超期事件":
                return 0;
            case "到期事件":
                return 1;
            case "待验收事件":
                return 2;
            case "自行处理事件":
                return 3;
            case "派发事件":
                return 4;
            case "已认领超期事件":
                return 5;
            case "待认领超期事件":
                return 6;
            default:
//            case "驳回事件":
                return 3;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_header, parent, false);
        return new RecyclerView.ViewHolder(view) {

        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((TextView) viewHolder.itemView).setText(getData().get(position).getClassify());
        (viewHolder.itemView).setBackgroundColor(Color.parseColor("#FAFBFF"));
    }

    private String handleShortTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setmType(int type, int isRenling) {
        this.isRenling = isRenling;
        this.mType = type;
    }
}
