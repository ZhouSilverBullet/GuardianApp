package com.sdxxtop.guardianapp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EventIndexBean;
import com.sdxxtop.guardianapp.ui.activity.EventReportDetailActivity;

public class TaskAgentsAdapter extends BaseQuickAdapter<EventIndexBean.EventBean, BaseViewHolder> {
    public TaskAgentsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventIndexBean.EventBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAddress = helper.getView(R.id.tv_address);
        TextView tvImportant = helper.getView(R.id.tv_important);
        TextView tvDate = helper.getView(R.id.tv_date);
        TextView tvPhase = helper.getView(R.id.tv_phase);

        tvTitle.setText(item.getTitle());
        tvAddress.setText(item.getPlace());
        int importantType = item.getImportant_type();
        StringBuilder sb = new StringBuilder().append("重要性：");
        switch (importantType) { //1:低 2:中 3:高
            case 1:
                sb.append("低");
                break;
            case 2:
                sb.append("中");
                break;
            case 3:
                sb.append("高");
                break;
        }

        tvImportant.setText(sb);

        String endDate = item.getEnd_date();
        tvDate.setText(endDate);

        String strStatus = "";
        switch (item.getStatus()) { //状态(1:带派发 2:待解决 3:待验收 4:验收通过 5:验收不通过)
            case 2:
                strStatus = "待解决";
                break;
            case 3:
                strStatus = "待验收";
                break;
            case 4:
                strStatus = "验收通过";
                break;
            case 5:
                strStatus = "验收不通过";
                break;
            default:
                strStatus = "带派发";
                break;
        }
        tvPhase.setText(strStatus);

        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventReportDetailActivity.startDetailActivity(v.getContext(), String.valueOf(item.getEvent_id()));
            }
        });
    }
}
