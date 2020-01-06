package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2019/12/31
 * Desc:
 */
public class KQST_Adapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private static final String TAG = "KQST_Adapter";
    public KQST_Adapter() {
        super(R.layout.item_kqst_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvTopLine = helper.getView(R.id.tvTopLine);
        TextView tvLineVer = helper.getView(R.id.tvLineVer);
        TextView tvLine = helper.getView(R.id.tvLineXXXX);
        tvTopLine.setVisibility(helper.getPosition() == 0 ? View.VISIBLE : View.GONE);
        tvLineVer.setVisibility(helper.getPosition() == 0 ? View.GONE : View.VISIBLE);
//
        helper.setText(R.id.tvStatus,item+helper.getAdapterPosition());
        if (helper.getLayoutPosition() == getData().size()-1) {
            tvLine.setVisibility(View.INVISIBLE);
        } else {
            tvLine.setVisibility(View.VISIBLE);
        }
    }
}
