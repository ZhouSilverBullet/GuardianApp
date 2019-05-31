package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.RequestBean;
import com.sdxxtop.guardianapp.presenter.ReCheckPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ReCheckContract;
import com.sdxxtop.guardianapp.ui.adapter.GridImageAdapter;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class ReCheckActivity extends BaseMvpActivity<ReCheckPresenter> implements ReCheckContract.IView, View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.btn_push)
    Button btnPush;

    private BottomSheetDialog bottomSheetDialog;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectImgList = new ArrayList<>();
    private List<LocalMedia> selectVideoList = new ArrayList<>();
    private List<LocalMedia> allDataList = new ArrayList<>();
    private int patrolId;

    @Override
    protected int getLayout() {
        return R.layout.activity_re_check;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initView() {
        super.initView();

        patrolId = getIntent().getIntExtra("patrol_id", 0);

        netContent.setEditHint("");
        netContent.setMaxLength(200);
        setPhotoRecycler(recyclerView);
    }

    @OnClick(R.id.btn_push)
    public void onViewClicked() {
        List<File> imagePushPath = getImageOrVideoPushPath(1);
        List<File> videoPushPath = getImageOrVideoPushPath(2);
        if (imagePushPath.size() == 0 && videoPushPath.size() == 0) {
            showToast("请选择图片或者视频");
            return;
        }

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }
        mPresenter.patrolHandle(editValue,imagePushPath, videoPushPath,patrolId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
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

                    for (LocalMedia media : localMedia) {
                        Log.i("图片-----》", media.getPath());
                    }
                    allDataList.clear();
                    allDataList.addAll(selectVideoList);
                    allDataList.addAll(selectImgList);
                    adapter.setList(allDataList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /************************   初始化  照片/视频 选择   *********************************/
    protected void setPhotoRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mAdapter = new EventReportRecyclerAdapter(R.layout.item_event_report_recycler);
        adapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
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
        bottomSheetDialog = new BottomSheetDialog(this);
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
                PictureSelector.create(ReCheckActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(8)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .selectionMedia(selectImgList)// 是否传入已选图片
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.tv_video:
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(ReCheckActivity.this)
                        .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .previewVideo(true)// 是否可预览视频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .selectionMedia(selectVideoList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
        }
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }

    //图片上传
    protected List<File> getImageOrVideoPushPath(int pictureType) {
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

    @Override
    public void showMsg(RequestBean bean) {
        UIUtils.showToast(bean.getMsg());
        finish();
    }
}
