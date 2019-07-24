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

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventModeBean;
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
import com.sdxxtop.guardianapp.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportActivity extends BaseMvpActivity<EventReportPresenter> implements EventReportContract.IView, ProvinceTwoPickerView.OnConfirmClick {
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
    @BindView(R.id.net_content_position)
    NumberEditTextView netContentPosition;

    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;

    private String lonLng;    //经纬度
    private int mSelectPartId;
    private EventSearchTitleAdapter adapter;

    private SingleStyleView singleStyleView;
    private int queryType;
    private ProvinceTwoPickerView pickerUtil;
    private boolean isItemClick = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report;
    }

    @Override
    protected void initView() {
        super.initView();
//        setSwipeBackEnable(true);

        netContentPosition.setMaxLength(60);
        tatvQuery.getTextRightText().setText("巡逻");
        tatvQuery.getTextRightText().setTextColor(Color.parseColor("#313131"));

        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        taevTitle.getEditText().setFilters(filters);

        netContent.setEditHint("");

        titleRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventSearchTitleAdapter(R.layout.item_text, null);
        titleRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isItemClick = true;
                tatvReportPath.getTextRightText().setText("请点击选择");
                tatvReportPath.getTextRightText().setTextColor(Color.parseColor("#999999"));
                EventSearchTitleBean.KeyInfo item = (EventSearchTitleBean.KeyInfo) adapter.getItem(position);
                taevTitle.getEditText().setText(item.getKeyword());
//                taevTitle.getEditText().setSelection(item.getKeyword().length());
                llSearchDataLayout.setVisibility(View.GONE);
                mPresenter.searchTitle(item.getKeyword(), item.getKeyword_id());
            }
        });


        if (GpsUtils.isOPen(this)) {
            LocationUtilOne oneLoaction = new LocationUtilOne();
            oneLoaction.startLocate(this);
            oneLoaction.setLocationCallBack(new LocationUtilOne.ILocationCallBack() {
                @Override
                public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                    String address = aMapLocation.getAddress();
                    if (!TextUtils.isEmpty(address)){
                        tatvHappen.getTextRightText().setText(address);
//                        String value = longitude + "," + latitude;
                        lonLng = aMapLocation.getLongitude()+","+aMapLocation.getLatitude();
                    }
                }
            });
        }
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
                if (isItemClick) {  //item被点击触发的搜索
                    isItemClick = false;
                } else {
                    TextView zhuguanbumen = tatvReportPath.getTextRightText();
                    zhuguanbumen.setText("");
                    zhuguanbumen.setHint("请点击选择");
                    mPresenter.searchTitle(s.toString().trim(), 0);
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
//        mPresenter.loadSector();
//        mPresenter.loadAera();
        mPresenter.searchTitle("", 0);
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

        //主管部门
        String pathName = tatvReportPath.getRightTVString();
        if (TextUtils.isEmpty(pathName) || mSelectPartId == 0) {
            showToast("请选择主管部门");
            return;
        }

        //发现方式
        String queryName = tatvQuery.getRightTVString();
        if (findType == null || TextUtils.isEmpty(queryName)) {
            showToast("请选择发现方式");
            return;
        }

        //发生地点
        String place = tatvHappen.getTextRightText().getText().toString().trim();
        if (TextUtils.isEmpty(place) || TextUtils.isEmpty(lonLng)) {
            showToast("请选择发生地点");
            return;
        }

        int pathType = mSelectPartId;

        String editValue = netContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
            showToast("请填写事件描述内容");
            return;
        }

        mPresenter.pushReport(title, pathType, queryType, place, lonLng, editValue, imagePushPath, vedioPushPath, netContentPosition.getEditValue());
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
//        mPartList = par;
    }

    /**
     * 展示标题输入联想
     *
     * @param bean
     */
    @Override
    public void showSearchData(EventSearchTitleBean bean, int keywordId) {
        List<EventShowBean.NewPartBean> mPartList = bean.getPart_info();
        if (mPartList != null) {
            pickerUtil = new ProvinceTwoPickerView(this, mPartList);
            pickerUtil.setOnConfirmClick(this);
        }

        if (keywordId == 0) {   // 标题搜索 展示
            if (bean.getKey_info() != null && bean.getKey_info().size() > 0) {
                llSearchDataLayout.setVisibility(View.VISIBLE);
                adapter.replaceData(bean.getKey_info());
            } else {
                llSearchDataLayout.setVisibility(View.GONE);
            }
        } else {    // 点击列表item不展示联想
            llSearchDataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 展示根据部门id搜索出来的发现方式
     *
     * @param bean
     */
    @Override
    public void showQuerySelect(EventModeBean bean) {
        findType.clear();
        if (bean.getMode_data() != null && bean.getMode_data().size() > 0) {
            for (int i = 0; i < bean.getMode_data().size(); i++) {
                EventModeBean.ModeDataBean dataBean = bean.getMode_data().get(i);
                findType.add(new SingleStyleView.ListDataBean(dataBean.getMode_id(), dataBean.getName()));
            }
            if (singleStyleView == null) {
                singleStyleView = new SingleStyleView(this, null);
                singleStyleView.setOnItemSelectLintener(new SingleStyleView.OnItemSelectLintener() {
                    @Override
                    public void onItemSelect(int id, String result) {
                        queryType = id;
                        tatvQuery.getTextRightText().setText(result);
                    }
                });
            }
            singleStyleView.replaceData(findType);
            singleStyleView.show();
        } else {
            showToast("暂无数据");
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


    @OnClick({R.id.tatv_query, R.id.tatv_happen, R.id.tatv_report_path})
    public void onViewClicked(View view) {

        String title = taevTitle.getEditText().getText().toString().trim();
        hideKeyboard(btnPush);

        switch (view.getId()) {
            case R.id.tatv_query:
                if (1 == 1) {
                    return;
                }
                //事件标题
                if (TextUtils.isEmpty(title)) {
                    showToast("请填写事件标题");
                    return;
                }
                //发现方式
                mPresenter.eventMode(mSelectPartId);
                break;
            case R.id.tatv_happen:
                selectHappen();
                break;
            case R.id.tatv_report_path:  // 主管部门
                //事件标题
//                if (TextUtils.isEmpty(title)) {
//                    showToast("请填写事件标题");
//                    return;
//                }

                if (pickerUtil != null) {
                    pickerUtil.show();
                }
                break;
            default:
                break;
        }
    }

    private List<SingleStyleView.ListDataBean> findType = new ArrayList<>();

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
        mSelectPartId = resultId;
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

}
