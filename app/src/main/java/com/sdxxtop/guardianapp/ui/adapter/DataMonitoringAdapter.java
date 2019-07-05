package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.AuthDataBean;
import com.sdxxtop.guardianapp.ui.activity.MonitorMapActivity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/7/4
 * Desc:
 */
public class DataMonitoringAdapter extends BaseQuickAdapter<AuthDataBean.AuthBean, BaseViewHolder> {

    public DataMonitoringAdapter(int layoutResId, @Nullable List<AuthDataBean.AuthBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthDataBean.AuthBean item) {
        helper.setText(R.id.tv_title, item.getTitle());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (item.getType()) {
                    case 1:   // 扬尘监测
                        intent = new Intent(mContext, MonitorMapActivity.class);
                        break;
                }

                if (intent!=null){
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
