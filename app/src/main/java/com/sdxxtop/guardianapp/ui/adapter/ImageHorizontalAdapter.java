package com.sdxxtop.guardianapp.ui.adapter;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;
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

import static android.os.Environment.DIRECTORY_PICTURES;

public class ImageHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<LocalMedia> selectList = new ArrayList<>();

    public ImageHorizontalAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item)) {
            Glide.with(mContext).load(item).into(imageView);
        }

        List<String> data = getData();
        if (data != null && data.size() > 0) {
            selectList.clear();
            for (int i = 0; i < data.size(); i++) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(data.get(i));
                selectList.add(localMedia);
            }
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(helper.getAdapterPosition(),
                        Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).getPath(), selectList);
            }
        });
    }
}
