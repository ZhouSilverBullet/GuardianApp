package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventSearchTitleBean;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
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
    @BindView(R.id.tv_select)
    TextView tvSelect;


    private String lonLng;    //经纬度
    private int part_id;   // 选择的部门id
    private int category_id;   // 事件分类id
    private boolean isItemClick = false;


    private EventSearchTitleAdapter adapter;
    private List<SingleStyleView.ListDataBean> categoryList = new ArrayList<>();
    private ProvinceTwoPickerView pickerUtil;
    private SingleStyleView categorySelectView;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
        netContentPosition.setMaxLength(60);
        cvisvView.setTvDesc(true);
        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        taevTitle.getEditText().setFilters(filters);

        netContent.setEditHint("");

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventSearchTitleAdapter();
        titleRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isItemClick = true;
                ShowPartBean.KeywordInfoBean item = (ShowPartBean.KeywordInfoBean) adapter.getItem(position);
                taevTitle.getEditText().setText(item.classify_keyword);
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
                    if (!TextUtils.isEmpty(address)) {
                        tvPlaceTitle.setText(address);
//                        String value = longitude + "," + latitude;
                        lonLng = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                    }
                }
            });
        }
        categorySelectView = new SingleStyleView(this, categoryList);
        categorySelectView.setOnItemSelectLintener(this);
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
    protected void initData() {
        super.initData();
        mPresenter.searchTitle();
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

        if ((imagePushPath.size() + vedioPushPath.size()) < 3) {
            showToast("需要提供3个以上图片或者视频");
            return;
        }

        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写事件标题");
            return;
        }

        //事件分类
        if (category_id == 0) {
            showToast("请选择事件分类");
            return;
        }

        //主管部门
        String pathName = tatvReportPath.getRightTVString();
        if (TextUtils.isEmpty(pathName) || part_id == 0) {
            showToast("请选择主管部门");
            return;
        }

        //发生地点
        String place = tvPlaceTitle.getText().toString();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        int pathType = part_id;

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        mPresenter.pushReport(title, pathType, place, lonLng, editValue, imagePushPath, vedioPushPath, netContentPosition.getEditValue(), category_id);
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
        List<EventShowBean.NewPartBean> mPartList = bean.part_info;
        if (mPartList != null) {
            pickerUtil = new ProvinceTwoPickerView(this, mPartList);
            pickerUtil.setOnConfirmClick(this);
        }

        EventSearchTitleBean.PartBean part = bean.part;
        if (part != null) {
            part_id = part.part_id;
            tatvReportPath.getTextRightText().setText(part.part_name);
        }

        //分类信息
        List<EventSearchTitleBean.CategoryBean> category = bean.category;
        categoryList.clear();
        for (EventSearchTitleBean.CategoryBean item : category) {
            categoryList.add(new SingleStyleView.ListDataBean(item.category_id, item.category_name));
        }
        categorySelectView.replaceData(categoryList);
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
            tvSelect.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.tatv_report_path, R.id.tatv_event_type, R.id.col_happen})
    public void onViewClicked(View view) {
        String title = taevTitle.getEditText().getText().toString().trim();
        hideKeyboard(btnPush);
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
}
