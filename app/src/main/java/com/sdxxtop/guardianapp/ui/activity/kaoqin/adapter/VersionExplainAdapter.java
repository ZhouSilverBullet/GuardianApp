package com.sdxxtop.guardianapp.ui.activity.kaoqin.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

/**
 * @author :  lwb
 * Date: 2020/1/5
 * Desc:
 */
public class VersionExplainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public VersionExplainAdapter() {
        super(R.layout.item_version_explain);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tvLine = helper.getView(R.id.tv_line);
        TextView tvVerLine = helper.getView(R.id.tv_ver_line);
        tvLine.setVisibility(helper.getLayoutPosition() == 0 ? View.VISIBLE : View.GONE);
        tvVerLine.setVisibility(helper.getLayoutPosition() == getData().size() - 1 ? View.INVISIBLE : View.VISIBLE);
    }
}
