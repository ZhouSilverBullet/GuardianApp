package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.DataEntry;
import com.sdxxtop.guardianapp.ui.widget.RoundRectImageView;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.holder.MZViewHolder;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class BannerViewHolder implements MZViewHolder<DataEntry> {
    private RoundRectImageView mImageView;
    private TextView tvTitle;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = view.findViewById(R.id.banner_image);
        tvTitle = view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void onBind(Context context, int position, DataEntry data) {
        // 数据绑定
        mImageView.setImageResource(data.resId);
        tvTitle.setText(data.title);
    }
}
