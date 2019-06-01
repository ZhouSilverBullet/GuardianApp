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

    private List<LocalMedia> selectList = new ArrayList<>();

    public PatrolDetailImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setGone(R.id.ll_del, false);
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.fiv));
        List<String> data = getData();
        if (data!=null&&data.size()>0){
            selectList.clear();
            for (int i = 0; i < data.size(); i++) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(data.get(i));
                selectList.add(localMedia);
            }
        }


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(helper.getAdapterPosition(), selectList);
            }
        });
    }
}
