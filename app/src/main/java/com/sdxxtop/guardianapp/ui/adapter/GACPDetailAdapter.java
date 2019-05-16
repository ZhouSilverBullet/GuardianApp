package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.GridreportPatrolBean;
import com.sdxxtop.guardianapp.ui.activity.SafeStaffDetailActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class GACPDetailAdapter extends BaseQuickAdapter<GridreportPatrolBean.TrailUser, BaseViewHolder> {

    public GACPDetailAdapter(int layoutResId, @Nullable List<GridreportPatrolBean.TrailUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GridreportPatrolBean.TrailUser item) {

        helper.setText(R.id.tv_company_name, item.getName());
        helper.setText(R.id.tv_company_row, item.getPart_name());
        helper.setText(R.id.tv_seu_count, String.valueOf(item.getDays()));
        helper.setText(R.id.tv_train_count, String.valueOf(item.getDistance()));
        helper.setGone(R.id.tv_check_num, false);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SafeStaffDetailActivity.class);
                intent.putExtra("id", item.getUserid());
                intent.putExtra("name", item.getName());
                intent.putExtra("type", 1);
                mContext.startActivity(intent);
            }
        });
    }
}
