package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EarlyWarningBean;
import com.sdxxtop.guardianapp.presenter.DeviceWarnPresenter;
import com.sdxxtop.guardianapp.presenter.contract.DeviceWarnContract;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceWarnDetailActivity extends BaseMvpActivity<DeviceWarnPresenter> implements DeviceWarnContract.IView, ERCheckResultWindow.OnConfirmClick {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_gongdi)
    TextView tvGongdi;
    @BindView(R.id.tv_fuzeren)
    TextView tvFuzeren;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_no)
    Button btnNo;
    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.ll_btn_containor)
    LinearLayout llBtnContainor;
    @BindView(R.id.popwindow_bg)
    View popwindow_bg;

    private int earlyId;
    private ERCheckResultWindow erCheckResultWindow;

    @Override
    protected int getLayout() {
        return R.layout.activity_device_warn;
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
        earlyId = getIntent().getIntExtra("early_id", -1);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData(earlyId);
    }

    @Override
    public void showData(EarlyWarningBean bean) {
        if (bean == null) {
            tvAddress.setText("...");
            tvGongdi.setText("所属工地：...");
            tvFuzeren.setText("负责人：...");
            tvPhone.setText("联系电话：...");
            tvTime.setText("预警时间：...");
            tvContent.setText("预警内容：...");
            return;
        }
        tvAddress.setText(bean.getAddress());
        tvGongdi.setText("所属工地：" + bean.getSite_name());
        tvFuzeren.setText("负责人：" + bean.getLiable_name());
        tvPhone.setText("联系电话：" + bean.getLiable_mobile());
        tvTime.setText("预警时间：" + getFormatTime(bean.getAdd_time()));
        tvContent.setText("预警内容：" + bean.getEarly_content());

        if (bean.getIs_finish() == 1) {
            llBtnContainor.setVisibility(View.VISIBLE);
        } else {
            llBtnContainor.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_no, R.id.btn_yes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                if (erCheckResultWindow == null) {
                    erCheckResultWindow = new ERCheckResultWindow(this,popwindow_bg);
                    erCheckResultWindow.show(getLayout(), false);
                    erCheckResultWindow.setOnConfirmClick(this);
                } else {
                    erCheckResultWindow.show(getLayout(), false);
                }
                break;
            case R.id.btn_yes:
                mPresenter.deviceModify(earlyId, 3, "");
                break;
        }
    }

    public String getFormatTime(String time) {
        String result = "";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        try {
            Date parse = format1.parse(time);
            result = format2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onButtonClick(String str) {
        mPresenter.deviceModify(earlyId, 2, str);
        if (erCheckResultWindow != null) {
            erCheckResultWindow.dismiss();
        }
    }
}
