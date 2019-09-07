package com.sdxxtop.guardianapp.ui.activity;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventShowBean;
import com.sdxxtop.guardianapp.model.bean.FlyEventPartBean;
import com.sdxxtop.guardianapp.presenter.FlyEventReportPresenter;
import com.sdxxtop.guardianapp.presenter.contract.FlyEventReportContract;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SingleStyleView;
import com.sdxxtop.guardianapp.ui.widget.TextAndEditView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;
import com.sdxxtop.guardianapp.ui.widget.timePicker.ProvinceTwoPickerView;
import com.sdxxtop.guardianapp.utils.AMapFindLocation2;
import com.sdxxtop.guardianapp.utils.GpsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FlyEventReportActivity extends BaseMvpActivity<FlyEventReportPresenter> implements FlyEventReportContract.IView,
        ProvinceTwoPickerView.OnConfirmClick, SingleStyleView.OnItemSelectLintener {

    @BindView(R.id.tv_name)
    TextView tatvName;
    @BindView(R.id.tatv_part)
    TextAndTextView tatvPart;
    @BindView(R.id.tatv_uav)
    TextAndTextView tatvUav;
    @BindView(R.id.taev_title)
    TextAndEditView taevTitle;
    @BindView(R.id.net_content)
    NumberEditTextView netContent;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private ProvinceTwoPickerView pickerUtil;
    private SingleStyleView singleStyleView;
    private int mSelectPartId = -1;
    private int mSelectUavId = -1;
    private String location = "";
    private String reportAddress = "";

    @Override
    protected int getLayout() {
        return R.layout.activity_fly_event_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initData() {
        mPresenter.getPart();
        getLongitude();
    }

    @Override
    protected void initView() {
        InputFilter[] filters = {new InputFilter.LengthFilter(10)};
        taevTitle.getEditText().setFilters(filters);
        netContent.setEditHint(" ");
    }

    @Override
    public void showPart(FlyEventPartBean bean) {
        tatvName.setText(bean.getUser().getName());

        List<EventShowBean.NewPartBean> mPartList = bean.getPart();
        if (mPartList != null) {
            pickerUtil = new ProvinceTwoPickerView(this, mPartList);
            pickerUtil.setOnConfirmClick(this);
        }
        List<SingleStyleView.ListDataBean> data = new ArrayList<>();
        List<FlyEventPartBean.UavInfoBean> uav = bean.getUav();
        if (uav != null) {
            for (FlyEventPartBean.UavInfoBean item : uav) {
                data.add(new SingleStyleView.ListDataBean(item.getId(), item.getName()));
            }
            if (singleStyleView == null) {
                singleStyleView = new SingleStyleView(this, data);
                singleStyleView.setOnItemSelectLintener(this);
            }
        }
    }

    @OnClick({R.id.tatv_part, R.id.tatv_uav, R.id.btn_add})
    public void onViewClicked(View view) {
        hideKeyboard(view);
        switch (view.getId()) {
            case R.id.tatv_part:
                if (pickerUtil != null) {
                    pickerUtil.show();
                }
                break;
            case R.id.tatv_uav:
                if (singleStyleView != null) {
                    singleStyleView.show();
                }
                break;
            case R.id.btn_add:
                reportEvent();
                break;
        }
    }

    /**
     * 上报部门
     */
    private void reportEvent() {
        String title = taevTitle.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请填写标题");
            return;
        }
        if (mSelectPartId == -1) {
            showToast("请选择部门");
            return;
        }
        if (mSelectUavId == -1) {
            showToast("请选择无人机");
            return;
        }
        String contentText = netContent.getEditValue();
        if (TextUtils.isEmpty(contentText)) {
            showToast("请填写任务描述");
            return;
        }
        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(reportAddress)) {
            showLoadingDialog();
            getLongitude();
        }
        mPresenter.reportEvent(title, mSelectPartId, mSelectUavId, location, reportAddress, contentText);
    }

    private void getLongitude() {
        if (GpsUtils.isOPen(this)) {
            AMapFindLocation2 instance = AMapFindLocation2.getInstance();
            instance.location();
            instance.setLocationCompanyListener(new AMapFindLocation2.LocationCompanyListener() {
                @Override
                public void onAddress(AMapLocation aMapLocation) {
                    String address = aMapLocation.getAddress();
                    if (TextUtils.isEmpty(address)) {
                        showToast("定位获取位置失败,请稍后重试");
                    } else {
                        hideLoadingDialog();
                        instance.stopLocation();
                        double longitude = aMapLocation.getLongitude();
                        double latitude = aMapLocation.getLatitude();
                        reportAddress = address;
                        location = longitude + "," + latitude;
                    }
                }
            });
        }
    }

    /**
     * 部门选中
     *
     * @param resultTx
     * @param resultId
     */
    @Override
    public void confirmClick(String resultTx, int resultId) {
        mSelectPartId = resultId;
        tatvPart.getTextRightText().setText(resultTx);
        tatvPart.getTextRightText().setTextColor(getResources().getColor(R.color.black));
    }

    /**
     * 无人机选中
     *
     * @param id
     * @param result
     */
    @Override
    public void onItemSelect(int id, String result) {
        mSelectUavId = id;
        tatvUav.getTextRightText().setText(result);
        tatvUav.getTextRightText().setTextColor(getResources().getColor(R.color.black));
    }
}
