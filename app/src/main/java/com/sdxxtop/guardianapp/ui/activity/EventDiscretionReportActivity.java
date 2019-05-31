package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.PatrolAddBean;
import com.sdxxtop.guardianapp.presenter.EventDiscretionReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventDiscretionReportContract;
import com.sdxxtop.guardianapp.ui.adapter.EventSearchTitleAdapter;
import com.sdxxtop.guardianapp.ui.adapter.GridImageAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.TextAndCheckBoxView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.MyTextChangeListener;
import com.sdxxtop.guardianapp.utils.TimeSelectBottomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventDiscretionReportActivity extends BaseMvpActivity<EventDiscretionReportPresenter> implements EventDiscretionReportContract.IView,
        View.OnClickListener {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;
    @BindView(R.id.tatv_happen)
    TextAndTextView tatvHappen;
    @BindView(R.id.tatv_end_time)
    TextAndTextView tatvEndTime;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.tacbv_view)
    TextAndCheckBoxView tacbvView;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;
    @BindView(R.id.title_recycler)
    RecyclerView titleRecycler;
    @BindView(R.id.ll_search_data_layout)
    RelativeLayout llSearchDataLayout;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.titleView)
    TitleView titleView;

    private String lonLng;//经纬度
    private TimeSelectBottomDialog dialog;
    private BottomSheetDialog bottomSheetDialog;
    private List<LocalMedia> selectImgList = new ArrayList<>();
    private List<LocalMedia> selectVideoList = new ArrayList<>();
    private List<LocalMedia> allDataList = new ArrayList<>();

    private GridImageAdapter adapter;
    private EventSearchTitleAdapter titleAdapter;
    private boolean isSearchEnable;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_discretion_report;
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
        tatvEndTime.setTextRightImage2Show(true);
        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
        netContent.setEditHint("");
        netContent.setMaxLength(200);
        setPhotoRecycler(mRecyclerView);
        tacbvView.setOnCheckBoxClick(new TextAndCheckBoxView.OnCheckBoxClick() {
            @Override
            public void refreshStatus() {
                tatvEndTime.getTextRightText().setText("");
                tatvEndTime.getTextRightText().setHint("请选择整改时效");
            }
        });

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        titleAdapter = new EventSearchTitleAdapter(R.layout.item_text, null);
        titleRecycler.setAdapter(titleAdapter);
        titleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter titleAdapter, View view, int position) {
                isSearchEnable = false;
                EventSearchTitleBean.KeyInfo item = (EventSearchTitleBean.KeyInfo) titleAdapter.getItem(position);
                taevTitle.getEditText().setText(item.getKeyword());
                taevTitle.getEditText().setSelection(item.getKeyword().length());
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });

        taevTitle.getEditText().addTextChangedListener(new MyTextChangeListener(new MyTextChangeListener.OnTextChangedListener() {
            @Override
            public void textChange(String str) {
                if (isSearchEnable) {
                    mPresenter.searchTitle(str);
                } else {
                    isSearchEnable = true;
                }
            }
        }));

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });

        titleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDiscretionReportActivity.this, EventDiscretionListActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.btn_push, R.id.tatv_happen, R.id.tatv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_push:
                showReportConfirmDialog();
                break;
            case R.id.tatv_happen:
                selectHappen();
                break;
            case R.id.tatv_end_time:
                if (tacbvView.getEnableClick()) {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
                        dialog.show();
                    }
                }
                break;
        }

    }

    private void selectHappen() {
        //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        showLoadingDialog();
        Intent intent = new Intent(this, AmapPoiActivity.class);
        startActivityForResult(intent, 100);
    }

    private void showReportConfirmDialog() {
        new IosAlertDialog(this).builder()
                .setNegativeButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toReport();
                    }
                })
                .setHeightMsg("确定上报事件?")
                .show();
    }

    // todo 网络请求
    private void toReport() {
        List<File> imagePushPath = getImageOrVideoPushPath(1);
        List<File> videoPushPath = getImageOrVideoPushPath(2);
        if (imagePushPath.size() == 0 && videoPushPath.size() == 0) {
            showToast("请选择图片或者视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        //发生地点
        String place = tatvHappen.getRightTVString();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        String endTime = tatvEndTime.getRightTVString();
        if (tacbvView.getEnableClick() && TextUtils.isEmpty(endTime)) {
            showToast("请选择整改时效");
            return;
        }

        showLoadingDialog();
        mPresenter.pushReport(tacbvView.getEnableClick() ? 1 : 2, title, place, lonLng, editValue, endTime, imagePushPath, videoPushPath);
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


    /************************   获取地点/图片/视频 回调 *********************************/

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
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String address = data.getStringExtra("ad");
            String lt = data.getStringExtra("lt");
            lonLng = lt;
            tatvHappen.getTextRightText().setText(address);
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
                PictureSelector.create(EventDiscretionReportActivity.this)
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
                PictureSelector.create(EventDiscretionReportActivity.this)
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

    @Override
    public void skipDetail(PatrolAddBean bean) {
        hideLoadingDialog();
        Intent intent = new Intent(this, PatrolAddDetailActivity.class);
        intent.putExtra("patrol_id", bean.getPatrol_id());
        startActivity(intent);
        finish();
    }

    @Override
    public void showSearchData(EventSearchTitleBean bean) {
        if (bean.getKey_info() != null && bean.getKey_info().size() > 0) {
            llSearchDataLayout.setVisibility(View.VISIBLE);
            titleAdapter.replaceData(bean.getKey_info());
            hideKeyboard(taevTitle.getEditText());
        } else {
            llSearchDataLayout.setVisibility(View.GONE);
        }
    }
}
