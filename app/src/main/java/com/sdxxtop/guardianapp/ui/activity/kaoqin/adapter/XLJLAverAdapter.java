package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.XljlDateBean;

import java.util.List;

import static com.sdxxtop.guardianapp.ui.activity.kaoqin.DashLineView.dp2px;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class XLJLAverAdapter extends BaseQuickAdapter<XljlDateBean.SignLogBean, BaseViewHolder> {

    private float mMax;

    public XLJLAverAdapter() {
        super(R.layout.item_aver_xljl);
    }

    @Override
    protected void convert(BaseViewHolder helper, XljlDateBean.SignLogBean item) {
        TextView textBg = helper.getView(R.id.item_aver_text_bg);
        TextView textX = helper.getView(R.id.item_aver_text_x);
        TextView textValue = helper.getView(R.id.item_aver_text_value);

        float work_time = ((item.total_distance < 1 ? 1 : item.total_distance) / mMax) * 26;
        if (work_time > 26) {
            work_time = 26;
        }
        if (item.total_distance == 0) {
            work_time = 0;
        }

        textBg.setLayoutParams(new LinearLayout.LayoutParams(0, dp2px(mContext, 30), work_time));
        int position = helper.getAdapterPosition();
        if (position % 2 == 0) {
            textBg.setBackgroundColor(Color.parseColor("#FF3296FA"));
        } else {
            textBg.setBackgroundColor(Color.parseColor("#553296FA"));
        }
        textX.setText(item.sign_date);
        textValue.setText("" + item.total_distance);
    }

    public void setListData(List<XljlDateBean.SignLogBean> data, float max) {
        this.mMax = max;
        replaceData(data);
    }
}
