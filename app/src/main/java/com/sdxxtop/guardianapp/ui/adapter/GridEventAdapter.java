package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GridEventListBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity_new;
import com.sdxxtop.guardianapp.ui.activity.PatrolAddDetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/26
 * Desc:
 */
public class GridEventAdapter extends BaseQuickAdapter<GridEventListBean.GridListBean, BaseViewHolder> {

    private int mPathType;

    public GridEventAdapter(@Nullable List<GridEventListBean.GridListBean> data) {
        super(R.layout.item_grid_event, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GridEventListBean.GridListBean item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_time, getTimeStr(item.add_time));
        helper.setText(R.id.tv_place, item.place);
        helper.setText(R.id.tv_status, getStatusStr(item.status));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (mPathType==3){
                    intent = new Intent(mContext, PatrolAddDetailActivity.class);
                    intent.putExtra("patrol_id",item.patrol_id);
                }else{
                    intent = new Intent(mContext, EventReportDetailActivity_new.class);
                    intent.putExtra("eventId",String.valueOf(item.event_id));
                }
                intent.putExtra("isPartEvent",true);
                if (intent != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private String getStatusStr(int status) {
        String str = "";
        switch (mPathType) {
            case 0:
                switch (status) { //事件状态 2、待解决 3、待验收 4、已完成
                    case 2:
                        str = "待解决";
                        break;
                    case 3:
                        str = "待验收";
                        break;
                    case 4:
                        str = "已完成";
                        break;
                }
                break;
            case 1:
                switch (status) { //事件状态 1、待派发 2、待解决 3、待验收 4、已完成
                    case 1:
                        str = "待派发";
                        break;
                    case 2:
                        str = "待解决";
                        break;
                    case 3:
                        str = "待验收";
                        break;
                    case 4:
                        str = "已完成";
                        break;
                }
                break;
            case 2:
                switch (status) { //事件状态 2、已认领 3、待评价 4、已完成
                    case 2:
                        str = "已认领";
                        break;
                    case 3:
                        str = "待评价";
                        break;
                    case 4:
                        str = "已完成";
                        break;
                }
                break;
            case 3:
                switch (status) { //状态  1、待复查 2、已完成
                    case 1:
                        str = "待复查";
                        break;
                    case 2:
                        str = "已完成";
                        break;
                }
                break;
        }

        return str;
    }

    private String getTimeStr(String date) {
        String time = "上报时间：";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatResult = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parse = format.parse(date);
            time += formatResult.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public void setPathType(int pathType) {
        this.mPathType = pathType;
    }
}
