package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.RecordInfoBean;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class AssessDetailAdapter extends BaseQuickAdapter<RecordInfoBean.ListsBean, BaseViewHolder> {

    public AssessDetailAdapter() {
        super(R.layout.item_assess_detail_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordInfoBean.ListsBean item) {
        ImageView icon = helper.getView(R.id.icon);
        TextView tvTitle2 = helper.getView(R.id.tvTitle2);
        TextView tvTitle3 = helper.getView(R.id.tvTitle3);

        boolean isShowIcon = !TextUtils.isEmpty(item.event_title) || !TextUtils.isEmpty(item.category_name);
        icon.setVisibility(isShowIcon ? View.VISIBLE : View.INVISIBLE);
        helper.setText(R.id.tv_num, "" + (helper.getAdapterPosition() + 1));
        helper.setText(R.id.tv_time, "" + item.add_date);


        String title = "";
        if (item.type == 2) {
            title = item.title + ": " + item.data;
        } else {
            title = item.title;
        }
        helper.setText(R.id.tvTitle1, title);
        helper.setText(R.id.tvScore1, item.score);

        if (!TextUtils.isEmpty(item.event_title)) {
            helper.setText(R.id.tvTitle2, "事件标题：" + item.event_title);
        }

        if (!TextUtils.isEmpty(item.category_name)) {
            helper.setText(R.id.tvTitle3, "事件分类：" + item.category_name);
        }

        icon.setOnClickListener(v -> {
            tvTitle2.setVisibility(tvTitle2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            tvTitle3.setVisibility(tvTitle3.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            if (tvTitle2.getVisibility() == View.VISIBLE || tvTitle3.getVisibility() == View.VISIBLE) {
                icon.setImageResource(R.drawable.assess_icon_list_up);
            } else {
                icon.setImageResource(R.drawable.assess_icon_list_down);
            }
        });
    }
}
