package com.sdxxtop.openlive.ui.adpater;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.sdkagora.R;

import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-11-12 19:03
 * Version: 1.0
 * Description:
 */
public class VideoAdapter extends BaseQuickAdapter<ViewGroup, BaseViewHolder> {

    private final double mHeight;

    public VideoAdapter(@Nullable List<ViewGroup> data, double width) {
        super(R.layout.video_recycler, data);
        this.mHeight = width * 3 / 4;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewGroup item) {
        ViewGroup parentViewGroup = (ViewGroup) item.getParent();
        if (parentViewGroup != null) {
            parentViewGroup.removeAllViews();
        }
        ((LinearLayout) helper.itemView).addView(item, ViewGroup.LayoutParams.MATCH_PARENT, (int) mHeight);
    }
}
