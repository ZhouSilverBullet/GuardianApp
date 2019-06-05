package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.presenter.EventReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportContract;
import com.sdxxtop.guardianapp.ui.adapter.EventReportRecyclerAdapter;
import com.sdxxtop.guardianapp.ui.adapter.EventSearchTitleAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportActivity extends BaseMvpActivity<EventReportPresenter> implements EventReportRecyclerAdapter.HorListener, EventReportContract.IView {
    @BindView(R.id.tv_title)
    TitleView mTitleView;
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

    @BindView(R.id.ll_search_data_layout)
    RelativeLayout llSearchDataLayout;
    @BindView(R.id.title_recycler)
    RecyclerView titleRecycler;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;

    @BindView(R.id.net_content)
    NumberEditTextView netContent;

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;

    private EventReportRecyclerAdapter mAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    //金纬度
    private String lonLng;
    private List<ShowPartBean.PartBean> mPartList;
    private int mSelectPartId;
    private EventSearchTitleAdapter adapter;

    private boolean isSearchEnable = true;
    private SingleStyleView singleStyleView;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
//        setSwipeBackEnable(true);

//        setPhotoRecycler(mRecyclerView);

        if (queryData == null) {
            queryData = new ArrayList<>();
            queryData.add("巡检");
            queryData.add("化验");
            queryData.add("感应器报警");
            queryData.add("他人反应");
        }
        tatvQuery.getTextRightText().setText(queryData.get(0));

        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        taevTitle.getEditText().setFilters(filters);

        netContent.setEditHint("");

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventSearchTitleAdapter(R.layout.item_text, null);
        titleRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isSearchEnable = false;
                mSelectPartId = 0;
                tatvReportPath.getTextRightText().setText("请点击选择");
                tatvReportPath.getTextRightText().setTextColor(Color.parseColor("#999999"));
                EventSearchTitleBean.KeyInfo item = (EventSearchTitleBean.KeyInfo) adapter.getItem(position);
                taevTitle.getEditText().setText(item.getKeyword());
                taevTitle.getEditText().setSelection(item.getKeyword().length());
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });
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

        taevTitle.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isSearchEnable) {
                    mPresenter.searchTitle(s.toString().trim());
                } else {
                    isSearchEnable = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        mPresenter.loadAera();
        mPresenter.searchTitle("");
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

        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> vedioPushPath = cvisvView.getImageOrVideoPushPath(2);

        if (imagePushPath.size() == 0 && vedioPushPath.size() == 0) {
            showToast("请选择图片或视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        //发现方式
        String queryName = tatvQuery.getRightTVString();
        if (queryData == null || TextUtils.isEmpty(queryName)) {
            showToast("请选择发现方式");
            return;
        }

        //发生地点
        String place = tatvHappen.getRightTVString();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        int queryType = queryData.indexOf(queryName) + 1;

        //主管部门
        String pathName = tatvReportPath.getRightTVString();
        if (reportPathData == null || TextUtils.isEmpty(pathName) || mSelectPartId == 0) {
            showToast("请选择主管部门");
            return;
        }
        int pathType = mSelectPartId;

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        mPresenter.pushReport(title, pathType, queryType, place, lonLng, editValue, imagePushPath, vedioPushPath);
    }

    @Override
    public void pushSuccess(String eventId) {
        hideLoadingDialog();
        UIUtils.showToast("上报成功");
        Intent intent = new Intent(this, EventReportListActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
        finish();
    }

    @Override
    public void showPart(List<ShowPartBean.PartBean> par) {
        mPartList = par;
    }

    /**
     * 展示标题输入联想
     *
     * @param bean
     */
    @Override
    public void showSearchData(EventSearchTitleBean bean) {
        if (bean.getKey_info() != null && bean.getKey_info().size() > 0) {
            llSearchDataLayout.setVisibility(View.VISIBLE);
            adapter.replaceData(bean.getKey_info());
        } else {
            llSearchDataLayout.setVisibility(View.GONE);
        }

        if (bean.getPart_info() != null && bean.getPart_info().size() > 0) {
            mPartList = bean.getPart_info();
        }
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

    /**
     * 提交的时候不进行清除本身的localMediaList数据
     *
     * @return
     */
    public List<LocalMedia> getRemoveLocalListTemp() {
        List<LocalMedia> tempLocalMedia = new ArrayList<>();
        for (int i = 0; i < localMediaList.size(); i++) {
            if (localMediaList.get(i).getDuration() != -100) {
                tempLocalMedia.add(localMediaList.get(i));
            }
        }
        return tempLocalMedia;
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
            cvisvView.callActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String address = data.getStringExtra("ad");
            String lt = data.getStringExtra("lt");
            lonLng = lt;
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

        List<LocalMedia> localListTemp = getRemoveLocalListTemp();
        //设置相片
        List<File> imgList = new ArrayList<>();
        if (localListTemp != null && localListTemp.size() > 0) {
            for (int i = 0; i < localListTemp.size(); i++) {
                String path = localListTemp.get(i).getPath();
                if (TextUtils.isEmpty(path)) {
                    path = localListTemp.get(i).getCompressPath();
                }
                imgList.add(new File(path));
            }
        }
        return imgList;
    }

    @OnClick({R.id.tatv_query, R.id.tatv_happen, R.id.tatv_report_path})
    public void onViewClicked(View view) {
        hideKeyboard(btnPush);
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


    private SingleStyleView singleReportPathDataView;
    private List<String> queryData;
    private List<String> reportPathData = new ArrayList<>();

    private void selectReportPath() {
        if (mPartList == null) {
            showToast("数据拉取中...");
            if (mPresenter != null) { //再拉去一次
                mPresenter.loadAera();
            }
            return;
        }
        reportPathData.clear();
        for (ShowPartBean.PartBean partBean : mPartList) {
            reportPathData.add(partBean.getPart_name());
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
        showQuerySelect(queryData);
    }


    private void showReportPathSelect(List<String> queryData) {
        if (singleReportPathDataView == null) {
            singleReportPathDataView = new SingleStyleView(this, queryData);

            singleReportPathDataView.setOnItemSelectLintener(new SingleStyleView.OnItemSelectLintener() {
                @Override
                public void onItemSelect(String result) {
                    tatvReportPath.getTextRightText().setText(result);
                    tatvReportPath.getTextRightText().setTextColor(getResources().getColor(R.color.black));
                    if (mPartList != null) {
                        for (ShowPartBean.PartBean partBean : mPartList) {
                            if (result.equals(partBean.getPart_name())) {
                                mSelectPartId = partBean.getPart_id();
                                return;
                            }
                        }
                    }
                    mSelectPartId = 0;
                }
            });
        }

        singleReportPathDataView.replaceData(queryData);
        singleReportPathDataView.show();
    }

    private void showQuerySelect(List<String> queryData) {
        if (singleStyleView == null) {
            singleStyleView = new SingleStyleView(this, queryData);
            singleStyleView.setOnItemSelectLintener(new SingleStyleView.OnItemSelectLintener() {
                @Override
                public void onItemSelect(String result) {
                    tatvQuery.getTextRightText().setText(result);
                }
            });
        }
        singleStyleView.show();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }
}
