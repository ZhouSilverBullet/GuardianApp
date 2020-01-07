package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class AssessDetailAdapter extends BaseQuickAdapter<AssessDetailAdapter.DetailBean, BaseViewHolder> {

    public AssessDetailAdapter() {
        super(R.layout.item_assess_detail_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBean item) {
        ImageView icon = helper.getView(R.id.icon);
        TextView tvTitle2 = helper.getView(R.id.tvTitle2);
        TextView tvTitle3 = helper.getView(R.id.tvTitle3);

        icon.setVisibility(item.isShowIcon ? View.VISIBLE : View.INVISIBLE);
        helper.setText(R.id.tvTitle1, item.title1);
        helper.setText(R.id.tvScore1, item.score);
        helper.setText(R.id.tvTitle2, "事件标题：" + item.title2);
        helper.setText(R.id.tvTitle3, "事件分类：" + item.title3);


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

    public static class DetailBean {
        public String title1;
        public String title2;
        public String title3;
        public String score;

        public boolean isShowIcon;

        public DetailBean(String title1, String title2, String title3, String score) {
            this.title1 = title1;
            this.title2 = title2;
            this.title3 = title3;
            this.score = score;
            this.isShowIcon = !TextUtils.isEmpty(title2) || !TextUtils.isEmpty(title3);
        }
    }
}
