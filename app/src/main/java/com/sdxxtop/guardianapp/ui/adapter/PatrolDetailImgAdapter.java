package com.sdxxtop.guardianapp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :  lwb
 * Date: 2019/5/30
 * Desc:
 */
public class PatrolDetailImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PatrolDetailImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setGone(R.id.ll_del,false);
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.fiv));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<LocalMedia> selectList = new ArrayList<>();
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(item);
                selectList.add(localMedia);
                PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(0, selectList);
            }
        });
    }
}
