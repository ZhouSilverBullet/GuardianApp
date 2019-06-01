package com.sdxxtop.guardianapp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.model.bean.MediaBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class PatrolDetailImgAdapter extends BaseQuickAdapter<MediaBean, BaseViewHolder> {

    private List<LocalMedia> selectImgList = new ArrayList<>();
    private List<LocalMedia> selectVideoList = new ArrayList<>();

    public PatrolDetailImgAdapter(int layoutResId, @Nullable List<MediaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaBean item) {
        helper.setGone(R.id.ll_del, false);
        if (item.getType() == 1) {
            Glide.with(mContext).load(item.getPath()).into((ImageView) helper.getView(R.id.fiv));
        }
        LinearLayout view = helper.getView(R.id.rl_containor);
        view.setVisibility(item.getType() == 2 ? View.VISIBLE : View.GONE);

        List<MediaBean> data = getData();
        if (data != null && data.size() > 0) {
            selectImgList.clear();
            selectVideoList.clear();
            for (int i = 0; i < data.size(); i++) {
                LocalMedia localMedia = new LocalMedia();
                MediaBean mediaBean = data.get(i);
                localMedia.setPath(mediaBean.getPath());
                if (mediaBean.getType() == 1) {
                    selectImgList.add(localMedia);
                } else {
                    selectVideoList.add(localMedia);
                }
            }
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getType() == 2) {
                    // 预览视频
                    PictureSelector.create((Activity) mContext).externalPictureVideo(selectVideoList.get(0).getPath());
                } else {
                    int position;
                    if (selectVideoList.size() != 0) {
                        position = helper.getAdapterPosition()-1;
                    } else {
                        position = helper.getAdapterPosition();
                    }
                    PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectImgList);
                }
            }
        });
    }
}
