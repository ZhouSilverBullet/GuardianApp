package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.MainIndexBeanNew;
import com.sdxxtop.guardianapp.ui.activity.VideoPlay2Activity;
import com.sdxxtop.guardianapp.ui.widget.RoundRectImageView;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/9/5
 * Desc:
 */
public class HomeImageAdapter extends BaseQuickAdapter<MainIndexBeanNew.WheelPlantingVideo, BaseViewHolder> {

    public HomeImageAdapter(@Nullable List<MainIndexBeanNew.WheelPlantingVideo> data) {
        super(R.layout.item_only_img, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainIndexBeanNew.WheelPlantingVideo item) {
        RoundRectImageView ivIcon = helper.getView(R.id.iv_icon);
        TextView tv_line_1 = helper.getView(R.id.tv_line_1);
        if (helper.getAdapterPosition() == 0) {
            tv_line_1.setVisibility(View.VISIBLE);
        } else {
            tv_line_1.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(item.cover_img).into(ivIcon);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoPlay2Activity.class);
                intent.putExtra("video_path", "" + item.video_link);
                mContext.startActivity(intent);
            }
        });
    }
}
