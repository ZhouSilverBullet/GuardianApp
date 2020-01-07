package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQTJ_Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private static final String TAG = "KQTJ_Adapter";
    public KQTJ_Adapter() {
        super(R.layout.item_kqtj_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView nameText = helper.getView(R.id.item_statistical_day_name);
        TextView countText = helper.getView(R.id.item_statistical_day_count);
        int position = helper.getAdapterPosition();
        switch (position) {
            case 0:
                nameText.setText("工作天数");
                break;
            case 1:
                nameText.setText("迟到次数");
                break;
            case 2:
                nameText.setText("早退次数");
                break;
            case 3:
                nameText.setText("旷工次数");
                break;
        }
        countText.setText(item);
    }
}
