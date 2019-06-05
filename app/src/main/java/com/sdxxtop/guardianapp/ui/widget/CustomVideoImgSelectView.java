package com.sdxxtop.guardianapp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.adapter.GridImageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :  lwb
 * Date: 2019/5/31
 * Desc:
 */
public class CustomVideoImgSelectView extends LinearLayout implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.rv)
    RecyclerView rv;

    private GridImageAdapter adapter;
    private BottomSheetDialog bottomSheetDialog;
    private List<LocalMedia> selectImgList = new ArrayList<>();
    private List<LocalMedia> selectVideoList = new ArrayList<>();
    private List<LocalMedia> allDataList = new ArrayList<>();

    public CustomVideoImgSelectView(Context context) {
        this(context, null);
    }

    public CustomVideoImgSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoImgSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_video_img_select, this, true);
        ButterKnife.bind(this);
        setPhotoRecycler(rv);
    }

    /************************   初始化  照片/视频 选择   *********************************/
    protected void setPhotoRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mAdapter = new EventReportRecyclerAdapter(R.layout.item_event_report_recycler);
        adapter = new GridImageAdapter(this.getContext(), new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (bottomSheetDialog != null) {
                    bottomSheetDialog.show();
                } else {
                    initBottomDiaolg();
                    bottomSheetDialog.show();
                }
            }
        });
        recycler.setAdapter(adapter);
    }

    public void initBottomDiaolg() {
        bottomSheetDialog = new BottomSheetDialog(this.getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_picture_selector_bottom_layout);
        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(R.id.tv_photo).setOnClickListener(this);
        bottomSheetDialog.getDelegate().findViewById(R.id.tv_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (adapter != null) {
            List<LocalMedia> data = adapter.getData();
            selectImgList.clear();
            selectVideoList.clear();
            if (data != null && data.size() > 0) {
                for (LocalMedia localMedia : data) {
                    if (localMedia.getMimeType() == PictureConfig.TYPE_VIDEO) {
                        selectVideoList.add(localMedia);
                    } else {
                        selectImgList.add(localMedia);
                    }
                }
            }
        }

        switch (v.getId()) {
            case R.id.tv_photo:
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(selectVideoList.size()==0?9:8)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .selectionMedia(selectImgList)// 是否传入已选图片
                        .recordVideoSecond(30)
                        .compress(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.tv_video:
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create((Activity) getContext())
                        .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewVideo(true)// 是否可预览视频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .videoQuality(0)
                        .compress(true)
                        .recordVideoSecond(20)
                        .selectionMedia(selectVideoList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
        }
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }

    public void callActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    if (localMedia.size() == 1) {
                        if (localMedia.get(0).getMimeType() == PictureConfig.TYPE_VIDEO) {
                            selectVideoList = localMedia;
                        } else {
                            selectImgList = localMedia;
                        }
                    } else {
                        selectImgList = localMedia;
                    }

//                    for (LocalMedia media : localMedia) {
//                        Log.i("图片-----》", media.getPath());
//                    }
                    allDataList.clear();
                    allDataList.addAll(selectVideoList);
                    allDataList.addAll(selectImgList);
                    adapter.setList(allDataList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    //图片上传
    public List<File> getImageOrVideoPushPath(int pictureType) {
        //设置相片
        List<File> imgList = new ArrayList<>();
        List<File> videoList = new ArrayList<>();
        if (adapter != null) {
            List<LocalMedia> data = adapter.getData();
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    String path = data.get(i).getPath();
                    if (TextUtils.isEmpty(path)) {
                        path = data.get(i).getCompressPath();
                    }
                    if (data.get(i).getMimeType() == PictureConfig.TYPE_IMAGE) {
                        imgList.add(new File(path));
                    } else {
                        videoList.add(new File(path));
                    }
                }
            }
        }
        if (pictureType == 1) {
            return imgList;
        } else {
            return videoList;
        }
    }

}
