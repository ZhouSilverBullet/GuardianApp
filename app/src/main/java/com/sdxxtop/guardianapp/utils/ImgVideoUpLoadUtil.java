package com.sdxxtop.guardianapp.utils;

import android.app.Activity;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  lwb
 * Date: 2019/5/29
 * Desc:
 */
public class ImgVideoUpLoadUtil implements View.OnClickListener {

    private Activity mActivity;
    List<LocalMedia> imgMediaList = new ArrayList<>();
    List<LocalMedia> videoMediaList = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;

    public ImgVideoUpLoadUtil(Activity activity) {
        this.mActivity = activity;
        initBottomDiaolg();
    }

    public void show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
    }

      public void initBottomDiaolg() {
        bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(R.layout.dialog_picture_selector_bottom_layout);
        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(R.id.tv_photo).setOnClickListener(this);
        bottomSheetDialog.getDelegate().findViewById(R.id.tv_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_photo:
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(8)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.tv_video:
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(mActivity)
                        .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewVideo(true)// 是否可预览视频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
        }
        if (bottomSheetDialog!=null){
            bottomSheetDialog.dismiss();
        }
    }
}
