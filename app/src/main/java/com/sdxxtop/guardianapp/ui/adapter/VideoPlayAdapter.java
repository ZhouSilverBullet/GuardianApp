package com.sdxxtop.guardianapp.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/8/31
 * Desc:
 */
public class VideoPlayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public VideoPlayAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setIsRecyclable(false);
        ImageView ivVideo = helper.getView(R.id.iv_video);
        Glide.with(mContext).load("https://i0.hdslb.com/bfs/archive/d7db7d500c14548ae33fce679cd38a850c3f94be.png@880w_440h.png").into(ivVideo);
    }
}
