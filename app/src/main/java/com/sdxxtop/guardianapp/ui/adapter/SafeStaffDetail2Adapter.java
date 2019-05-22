package com.sdxxtop.guardianapp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.EnterpriseUserdetailsBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/10
 * Desc:
 */
public class SafeStaffDetail2Adapter extends BaseQuickAdapter<EnterpriseUserdetailsBean.UserInfo, BaseViewHolder> {

    public SafeStaffDetail2Adapter(int layoutResId, @Nullable List<EnterpriseUserdetailsBean.UserInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseUserdetailsBean.UserInfo item) {
        helper.setText(R.id.tv_time,item.getName());
        helper.setText(R.id.tv_distance,String.valueOf(item.getEvent_count()));
        helper.setText(R.id.tv_duration,String.valueOf(item.getSign_count()));
        helper.setText(R.id.tv_sign_num,String.valueOf(item.getTrai_count()));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PatrolPathActivity.class);
//                intent.putExtra("userid",item.getUserid());
//                intent.putExtra("name",item.getName());
//                mContext.startActivity(intent);
            }
        });
    }
}
