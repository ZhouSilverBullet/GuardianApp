package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
import com.sdxxtop.guardianapp.model.bean.ShowPartBean;
import com.sdxxtop.guardianapp.presenter.EventReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportContract;
import com.sdxxtop.guardianapp.ui.adapter.EventSearchTitleAdapter;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;
import com.sdxxtop.guardianapp.ui.widget.timePicker.ProvinceTwoPickerView;
import com.sdxxtop.guardianapp.utils.GpsUtils;
import com.sdxxtop.guardianapp.utils.LocationUtilOne;
import com.sdxxtop.guardianapp.utils.MyTextWatcher;
import com.sdxxtop.guardianapp.utils.StringUtil;
import com.sdxxtop.guardianapp.utils.TimeSelectBottomDialog;
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportActivity extends BaseMvpActivity<EventReportPresenter> implements EventReportContract.IView, ProvinceTwoPickerView.OnConfirmClick,
        SingleStyleView.OnItemSelectLintener {

    /******** 固定信息 *********/
    @BindView(R.id.report_name)
    TextAndTextView reportName;
    @BindView(R.id.report_phone)
    TextAndTextView reportPhone;
    @BindView(R.id.report_part)
    TextAndTextView reportPart;
    @BindView(R.id.find_type)
    TextAndTextView findType;

    @BindView(R.id.tv_title)
    TitleView mTitleView;
    @BindView(R.id.btn_push)
    Button btnPush;

    @BindView(R.id.tatv_report_path)
    TextAndTextView tatvReportPath;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;
    @BindView(R.id.tatv_event_type)
    TextAndTextView tatvEventType;

    @BindView(R.id.ll_search_data_layout)
    RelativeLayout llSearchDataLayout;
    @BindView(R.id.title_recycler)
    RecyclerView titleRecycler;
    @BindView(R.id.tv_dismiss)
    TextView tvDismiss;

    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.net_content_position)
    NumberEditTextView netContentPosition;

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.col_happen)
    ConstraintLayout col_happen;
    @BindView(R.id.tv_place_title)
    TextView tvPlaceTitle;
    @BindView(R.id.tv_place_desc)
    TextView tvPlaceDesc;
    @BindView(R.id.ll_location_layout)
    LinearLayout llLocationLayout;
    /*********** 复查 ***********/
    @BindView(R.id.ll_fucha_layout)
    LinearLayout llFuchaLayout;
    @BindView(R.id.cb_into_voice)
    CheckBox cbIntoVoice;
    @BindView(R.id.tatv_end_time)
    TextAndTextView tatvEndTime;


    private String lonLng;    //经纬度
    private int part_id;   // 选择的部门id
    private int category_id;   // 事件分类id
    private boolean isItemClick = false;
    private int streamId = 1;


    private EventSearchTitleAdapter adapter;
    private List<SingleStyleView.ListDataBean> categoryList = new ArrayList<>();
    private ProvinceTwoPickerView pickerUtil;
    private SingleStyleView categorySelectView;

    private TimeSelectBottomDialog dialog;
    private EventStreamReportBean.ReportPathBean streamEventPermission;

    private static final String TAG = "EventReportActivity";

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
        netContent.setEditHint("在此录入事件描述");

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventSearchTitleAdapter();
        titleRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isItemClick = true;
                ShowPartBean.KeywordInfoBean item = (ShowPartBean.KeywordInfoBean) adapter.getItem(position);
                EditText et_title = taevTitle.getEditText();
                et_title.setText(item.classify_keyword);
                et_title.clearFocus();
                hideKeyboard(taevTitle.getEditText());

                llSearchDataLayout.setVisibility(View.GONE);
                mPresenter.keywordMatch(item.classify_keyword, item.classify_keyword_id);
            }
        });


        if (GpsUtils.isOPen(this)) {
            LocationUtilOne oneLoaction = new LocationUtilOne();
            oneLoaction.startLocate(this);
            oneLoaction.setLocationCallBack(new LocationUtilOne.ILocationCallBack() {
                @Override
                public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                    String address = aMapLocation.getAddress();
                    String poiName = aMapLocation.getPoiName();
                    if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(poiName)) {
                        tvPlaceDesc.setText(address);
                        tvPlaceTitle.setText(poiName);
//                        String value = longitude + "," + latitude;
                        lonLng = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                        oneLoaction.stopLocation();
                    }
                }
            });
        }
        categorySelectView = new SingleStyleView(this, categoryList);
        categorySelectView.setOnItemSelectLintener(this);

        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());

        cbIntoVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatvEndTime.setVisibility(cbIntoVoice.isChecked() ? View.VISIBLE : View.GONE);
                tatvEndTime.getTextRightText().setText("");
                tatvEndTime.getTextRightText().setHint("请选择整改时效");
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        taevTitle.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isItemClick) {  //item被点击触发的搜索
                    isItemClick = false;
                } else {
                    mPresenter.keywordMatch(s.toString().trim(), 0);
                }
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
    protected void initVariables() {
        if (getIntent() != null) {
            streamId = getIntent().getIntExtra("streamId", 1);
        }
    }

    @Override
    protected void initData() {
        super.initData();
//        mPresenter.searchTitle();
        mPresenter.loadData(1);
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

    private int minSelectImg = 0;

    // todo 网络请求
    private void toReport() {

        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> vedioPushPath = cvisvView.getImageOrVideoPushPath(2);

        if (streamEventPermission == null) return;

        if (streamEventPermission.reportImg == 1 && (imagePushPath.size() + vedioPushPath.size()) < minSelectImg) {
            showToast("需要提供"+ minSelectImg+"个以上图片或者视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        //事件分类
        if (streamEventPermission.eventClassification == 1 && category_id == 0) {
            showToast("请选择事件分类");
            return;
        }

        //上报部门
        String pathName = tatvReportPath.getRightTVString();
        if (streamEventPermission.reportPart == 1 && (TextUtils.isEmpty(pathName) || part_id == 0)) {
            showToast("请选择主管部门");
            return;
        }
        int pathType = part_id;

        //发生地点
        String place = tvPlaceTitle.getText().toString();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        mPresenter.pushReport(
                title, pathType, place, lonLng, editValue, imagePushPath,
                vedioPushPath, netContentPosition.getEditValue(), category_id,
                streamEventPermission.basicReview == 2 ? 0 : (cbIntoVoice.isChecked() ? 1 : 2)  // 是否需要复查,需要选中为1,不选中2
                , streamId);
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

    /**
     * 展示搜索出来的标题
     *
     * @param bean
     */
    @Override
    public void showKeywordInfo(ShowPartBean bean, int keywordId) {
        ShowPartBean.CategoryInfoBean category = bean.category;
        if (category != null) {
            tatvEventType.getTextRightText().setText(category.category_name);
            category_id = category.category_id;
        }

        if (keywordId == 0) {   // 有id代表点击条目搜索不展示
            if (bean.list != null && bean.list.size() > 0) {
                llSearchDataLayout.setVisibility(View.VISIBLE);
                adapter.replaceData(bean.list);
            } else {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        } else {    // 点击列表item不展示联想
            llSearchDataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化信息
     *
     * @param bean
     */
    @Override
    public void showSearchData(EventSearchTitleBean bean) {
//        List<EventShowBean.NewPartBean> mPartList = bean.part_info;
//        if (mPartList != null) {
//            pickerUtil = new ProvinceTwoPickerView(this, mPartList);
//            pickerUtil.setOnConfirmClick(this);
//        }

//        EventSearchTitleBean.PartBean part = bean.part;
//        if (part != null) {
//            part_id = part.part_id;
//            tatvReportPath.getTextRightText().setText(part.part_name);
//        }

        //分类信息
//        List<EventSearchTitleBean.CategoryBean> category = bean.category;
//        categoryList.clear();
//        for (EventSearchTitleBean.CategoryBean item : category) {
//            categoryList.add(new SingleStyleView.ListDataBean(item.category_id, item.category_name));
//        }
//        categorySelectView.replaceData(categoryList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoadingDialog();
        if (resultCode == RESULT_OK) {
            cvisvView.callActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 100 && resultCode == 10087 && data != null) {
            String title = data.getStringExtra("ar");
            String desc = data.getStringExtra("ad");
            String lt = data.getStringExtra("lt");

            lonLng = lt;
            tvPlaceTitle.setText(title);
            tvPlaceDesc.setText(desc);
        }
    }


    @OnClick({R.id.tatv_report_path, R.id.tatv_event_type, R.id.col_happen, R.id.tatv_end_time})
    public void onViewClicked(View view) {
        String title = taevTitle.getEditText().getText().toString().trim();
        hideKeyboard(taevTitle.getEditText());
        switch (view.getId()) {
            case R.id.tatv_event_type:
                if (categorySelectView != null) {
                    categorySelectView.show();
                }
                break;
            case R.id.col_happen:
                selectHappen();
                break;
            case R.id.tatv_report_path:  // 主管部门
                if (pickerUtil != null) {
                    pickerUtil.show();
                }
                break;
            case R.id.tatv_end_time:
                if (cbIntoVoice.isChecked()) {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        dialog = new TimeSelectBottomDialog(this, tatvEndTime.getTextRightText());
                        dialog.show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void selectHappen() {
        //由于启动map界面比较慢,所以弄个进度条,在回到页面的情况下 hideLoadingDialog
        showLoadingDialog();
        Intent intent = new Intent(this, AmapPoiActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    public void showEventBean(EventShowBean bean) {
//        pickerUtil = new ProvinceTwoPickerView(this, bean.getPart());
//        pickerUtil.setOnConfirmClick(this);
    }

    @Override
    public void confirmClick(String resultTx, int resultId) {
        part_id = resultId;
        tatvReportPath.getTextRightText().setText(resultTx);
        tatvReportPath.getTextRightText().setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }

    /**
     * 选择事件分类结果
     *
     * @param id
     * @param result
     */
    @Override
    public void onItemSelect(int id, String result) {
        category_id = id;
        tatvEventType.getTextRightText().setText(result);
    }

    @Override
    public void setPermissionInfo(EventStreamReportBean bean) {
        //设置上报人信息
        EventStreamReportBean.UserBean user = bean.user;
        streamEventPermission = bean.reportPath;
        if (streamEventPermission != null) {
            reportName.setVisibility(streamEventPermission.username == 1 ? View.VISIBLE : View.GONE);  // 上报人
            reportPhone.setVisibility(streamEventPermission.userPhone == 1 ? View.VISIBLE : View.GONE);  // 上报人电话
            reportPart.setVisibility(streamEventPermission.userPart == 1 ? View.VISIBLE : View.GONE);  // 上报人部门
            findType.setVisibility(streamEventPermission.reportFind == 1 ? View.VISIBLE : View.GONE);  // 返现方式
            cvisvView.setVisibility(streamEventPermission.reportImg == 1 ? View.VISIBLE : View.GONE);  // 上报图片
            tatvReportPath.setVisibility(streamEventPermission.reportPart == 1 ? View.VISIBLE : View.GONE);  // 上报部门
            llLocationLayout.setVisibility(streamEventPermission.supplement == 1 ? View.VISIBLE : View.GONE);  // 定位补充描述
            tatvEventType.setVisibility(streamEventPermission.eventClassification == 1 ? View.VISIBLE : View.GONE);  // 事件分类
            llFuchaLayout.setVisibility(streamEventPermission.basicReview == 1 ? View.VISIBLE : View.GONE);  // 复查

            //定位补充 字数限制
            netContentPosition.setMaxLength(streamEventPermission.supplementNumber);
            //问题描述 字数限制
            netContent.setMaxLength(streamEventPermission.reportDescribe);
            //输入标题 字数限制
            StringUtil.setEditTextInhibitInputSpaChat(taevTitle.getEditText(), streamEventPermission.title);
            taevTitle.getEditText().setHint("事件类目关键词（限制" + streamEventPermission.title + "个字）");
            //选择图片和视频的总数量
//            cvisvView.setMaxImgCount(streamEventPermission.img);

            minSelectImg = streamEventPermission.img;

        }
        if (user != null) {
            reportName.getTextRightTextNoPadding().setText(user.name);
            reportPhone.getTextRightTextNoPadding().setText(user.mobile);
            reportPart.getTextRightTextNoPadding().setText(user.part_name);
        }


        //分类信息
        List<EventStreamReportBean.CategoryBean> category = bean.category;
        categoryList.clear();
        for (EventStreamReportBean.CategoryBean item : category) {
            categoryList.add(new SingleStyleView.ListDataBean(item.category_id, item.category_name));
        }
        categorySelectView.replaceData(categoryList);

        //默认部门
        EventStreamReportBean.PartBean part = bean.part;
        if (part != null) {
            part_id = part.part_id;
            tatvReportPath.getTextRightText().setText(part.part_name);
        }
        // 部门展示
        List<EventShowBean.NewPartBean> mPartList = bean.part_info;
        if (mPartList != null) {
            pickerUtil = new ProvinceTwoPickerView(this, mPartList);
            pickerUtil.setOnConfirmClick(this);
        }
    }
}
