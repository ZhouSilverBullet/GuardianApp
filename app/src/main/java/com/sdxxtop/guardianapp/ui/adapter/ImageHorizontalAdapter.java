package com.sdxxtop.guardianapp.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

import java.util.List;

import androidx.annotation.Nullable;

public class ImageHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageHorizontalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item)) {
            Glide.with(mContext).load(item).into(imageView);
        }
    }
}
