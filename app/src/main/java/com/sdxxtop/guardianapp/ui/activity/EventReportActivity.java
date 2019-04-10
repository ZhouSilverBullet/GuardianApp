package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventReportPresenter;
import com.sdxxtop.guardianapp.ui.adapter.EventReportRecyclerAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.SingleDataView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

public class EventReportActivity extends BaseMvpActivity<EventReportPresenter> implements EventReportRecyclerAdapter.HorListener {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_push)
    Button btnPush;

    @BindView(R.id.tatv_query)
    TextAndTextView tatvQuery;
    @BindView(R.id.tatv_happen)
    TextAndTextView tatvHappen;
    @BindView(R.id.tatv_report_path)
    TextAndTextView tatvReportPath;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;

    private EventReportRecyclerAdapter mAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
        setSwipeBackEnable(true);

        setPhotoRecycler(mRecyclerView);

        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        taevTitle.getEditText().setFilters(filters);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UIUtils.showToast("上传成功");
//                finish();
                showReportConfirmDialog();
            }
        });

        mTitleView.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventReportListActivity.class);
                startActivity(intent);
            }
        });
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

        List<File> imagePushPath = getImagePushPath();

        showLoadingDialog();
        mPresenter.pushReport(imagePushPath);
    }

    protected void setPhotoRecycler(RecyclerView recycler) {
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new EventReportRecyclerAdapter(R.layout.item_event_report_recycler);
        recycler.setAdapter(mAdapter);
        LocalMedia localMedia = new LocalMedia();
//        localMedia.setDuration(-1);
//        localMedia.setCompressPath("");
//        localMedia.setPath("");
//        localMediaList.add(localMedia);
//        localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        localMediaList.add(localMedia);
        mAdapter.addData(localMediaList);
        mAdapter.setListener(this);
    }

    @Override
    public void click() {
        goGallery();
    }

    public void removeLocalListTemp() {
        for (int i = 0; i < localMediaList.size(); i++) {
            if (localMediaList.get(i).getDuration() == -100) {
                localMediaList.remove(localMediaList.get(i));
                break;
            }
        }
    }

    public void goGallery() {
        removeLocalListTemp();

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .selectionMedia(localMediaList)
                .maxSelectNum(9).forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void delete(LocalMedia item) {
        localMediaList.remove(item);
        for (int i = 0; i < localMediaList.size(); i++) {
            LocalMedia media = localMediaList.get(i);
            if (media.getDuration() == -100) {
                break;
            }
            if (i == localMediaList.size() - 1) {
                localMediaList.add(getTemp());
            }
        }

        //删除也是重新刷新数据
        //本来想定义 onDelete回调，发现也是 adapter.replaceData(),所以也用onResult 了
        onResult(localMediaList);
    }

    protected void onResult(List<LocalMedia> selectList) {
        if (mAdapter != null) {
            mAdapter.replaceData(selectList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {
                        localMediaList.clear();
                        int size = selectList.size();
                        if (size < 9) {
                            localMediaList.addAll(selectList);
                            localMediaList.add(getTemp());
                        } else {
                            localMediaList.addAll(selectList);
                        }
                        onResult(localMediaList);
                    }
                    break;
            }
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String address = data.getStringExtra("ad");
            tatvHappen.getTextRightText().setText(address);
        }
    }

    protected LocalMedia getTemp() {
        LocalMedia localMedia = new LocalMedia();
        localMedia.setDuration(-100);
        return localMedia;
    }

    //图片上传
    protected List<File> getImagePushPath() {
        removeLocalListTemp();
        //设置相片
        List<File> imgList = new ArrayList<>();
        if (localMediaList != null && localMediaList.size() > 0) {
            for (int i = 0; i < localMediaList.size(); i++) {
                String path = localMediaList.get(i).getPath();
                if (TextUtils.isEmpty(path)) {
                    path = localMediaList.get(i).getCompressPath();
                }
                imgList.add(new File(path));
            }
        }
        return imgList;
    }

    @OnClick({R.id.tatv_query, R.id.tatv_happen, R.id.tatv_report_path})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tatv_query:
                selectQuery();
                break;
            case R.id.tatv_happen:
                selectHappen();
                break;
            case R.id.tatv_report_path:
                selectReportPath();
                break;
            default:
                break;
        }
    }


    private SingleDataView singleDataView;
    private SingleDataView singleReportPathDataView;
    private List<String> queryData;
    private List<String> reportPathData;

    private void selectReportPath() {
        if (reportPathData == null) {
            reportPathData = new ArrayList<>();
            reportPathData.add("环保局");
            reportPathData.add("城管局");
            reportPathData.add("安监局");
            reportPathData.add("住建局");
        }

        showReportPathSelect(reportPathData);
    }

    private void selectHappen() {
        //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        showLoadingDialog();
        Intent intent = new Intent(this, AmapPoiActivity.class);
        startActivityForResult(intent, 100);
    }

    private void selectQuery() {
        if (queryData == null) {
            queryData = new ArrayList<>();
            queryData.add("巡逻");
            queryData.add("化验");
            queryData.add("感应器报警");
            queryData.add("他人反应");
        }
        showQuerySelect(queryData);
    }

    private void showReportPathSelect(List<String> queryData) {
        if (singleReportPathDataView == null) {
            singleReportPathDataView = new SingleDataView(this, queryData);
        }

        singleReportPathDataView.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tatvReportPath.getTextRightText().setText(item);
            }
        });
        singleReportPathDataView.show();
    }

    private void showQuerySelect(List<String> queryData) {
        if (singleDataView == null) {
            singleDataView = new SingleDataView(this, queryData);
        }

        singleDataView.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tatvQuery.getTextRightText().setText(item);
            }
        });
        singleDataView.show();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }
}
