package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

import static com.sdxxtop.guardianapp.ui.activity.kaoqin.DashLineView.dp2px;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class AverAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AverAdapter() {
        super(R.layout.item_aver_recycler);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textBg = helper.getView(R.id.item_aver_text_bg);
        TextView textX = helper.getView(R.id.item_aver_text_x);
        TextView textValue = helper.getView(R.id.item_aver_text_value);
        float work_time = 10;
        if (work_time > 24) {
            work_time = 24f;
        }
        textBg.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(mContext, 30), work_time));
        int position = helper.getAdapterPosition();
        if (position % 2 == 0) {
            textBg.setBackgroundColor(Color.parseColor("#FF3296FA"));
        } else {
            textBg.setBackgroundColor(Color.parseColor("#553296FA"));
        }
        textX.setText("2020/01/05");
        textValue.setText(work_time + "小时");
    }
}
