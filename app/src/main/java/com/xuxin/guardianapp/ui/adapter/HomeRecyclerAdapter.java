package com.xuxin.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuxin.guardianapp.R;
import com.xuxin.guardianapp.ui.activity.EventReportActivity;
import com.xuxin.guardianapp.utils.UIUtils;

import java.util.List;

import androidx.annotation.Nullable;

public class HomeRecyclerAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public HomeRecyclerAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        ImageView itemImageView = helper.getView(R.id.item_iv);
        itemImageView.setImageResource(item);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showToast("" + item);
                Intent intent = null;
                switch (item) {
                    case R.drawable.list_1:
                        break;
                    case R.drawable.list_2:
                        intent = new Intent(mContext, EventReportActivity.class);
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
    }
}
