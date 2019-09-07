package com.sdxxtop.guardianapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.AllarticleBean;
import com.sdxxtop.guardianapp.ui.activity.NewsDetailsActivity;
import com.sdxxtop.guardianapp.ui.widget.RoundRectImageView;
import com.sdxxtop.guardianapp.ui.widget.mzbanner.holder.MZViewHolder;

/**
 * @author :  lwb
 * Date: 2019/9/6
 * Desc:
 */
public class BannerViewLearnHolder implements MZViewHolder<AllarticleBean.WheelPlanting> {
    private RoundRectImageView mImageView;
    private TextView tvTitle;
    private TextView tvDesc;

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = view.findViewById(R.id.banner_image);
        tvTitle = view.findViewById(R.id.tv_title);
        tvDesc = view.findViewById(R.id.tv_desc);
        return view;
    }

    @Override
    public void onBind(Context context, int position, AllarticleBean.WheelPlanting data) {
        // 数据绑定
        Glide.with(context).load(data.cover_img).into(mImageView);
        tvTitle.setText(data.title);
        tvTitle.setVisibility(View.GONE);
        tvDesc.setVisibility(View.VISIBLE);
        tvDesc.setText(data.title);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("article_path", data.new_url);
                context.startActivity(intent);
            }
        });
    }
}
