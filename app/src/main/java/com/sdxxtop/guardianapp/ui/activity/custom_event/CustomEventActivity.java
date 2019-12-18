package com.sdxxtop.guardianapp.ui.activity.custom_event;

import android.view.View;
import android.widget.LinearLayout;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
import com.sdxxtop.guardianapp.presenter.CustomEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CustomEventContract;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;

import butterknife.BindView;

public class CustomEventActivity extends BaseMvpActivity<CustomEventPresenter> implements CustomEventContract.IView {

    @BindView(R.id.report_name)
    TextAndTextView reportName;
    @BindView(R.id.report_phone)
    TextAndTextView reportPhone;
    @BindView(R.id.report_part)
    TextAndTextView reportPart;
    @BindView(R.id.find_type)
    TextAndTextView findType;
    /***** 音视频view ******/
    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    /***** 上报部门 ******/
    @BindView(R.id.tatv_report_path)
    TextAndTextView tatvReportPath;
    /***** 事件分类 ******/
    @BindView(R.id.tatv_event_type)
    TextAndTextView tatvEventType;
    /***** 定位补充描述 ******/
    @BindView(R.id.ll_location_layout)
    LinearLayout llLocationLayout;
    /***** 内容 ******/
    @BindView(R.id.net_content)
    NumberEditTextView netContent;


    private int streamId;

    @Override
    protected int getLayout() {
        return R.layout.activity_custom_event;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            streamId = getIntent().getIntExtra("streamId", 0);
        }
    }

    @Override
    protected void initData() {
        mPresenter.loadData(1);
    }

    @Override
    protected void initView() {
        cvisvView.setTvDesc(true);
        netContent.setEditHint("在此录入事件描述");

    }

    @Override
    public void setData(EventStreamReportBean bean) {
        //设置上报人信息
        EventStreamReportBean.UserBean user = bean.user;
        EventStreamReportBean.ReportPathBean reportPath = bean.reportPath;
        if (reportPath!=null){
            reportName.setVisibility(reportPath.username == 1 ? View.VISIBLE : View.GONE);  // 上报人
            reportPhone.setVisibility(reportPath.userPhone == 1 ? View.VISIBLE : View.GONE);  // 上报人电话
            reportPart.setVisibility(reportPath.userPart == 1 ? View.VISIBLE : View.GONE);  // 上报人部门
            findType.setVisibility(reportPath.reportFind == 1 ? View.VISIBLE : View.GONE);  // 返现方式
            cvisvView.setVisibility(reportPath.reportImg == 1 ? View.VISIBLE : View.GONE);  // 上报图片
            tatvReportPath.setVisibility(reportPath.reportPart == 1 ? View.VISIBLE : View.GONE);  // 上报部门
            llLocationLayout.setVisibility(reportPath.supplement == 1 ? View.VISIBLE : View.GONE);  // 定位补充描述
            tatvEventType.setVisibility(reportPath.eventClassification == 1 ? View.VISIBLE : View.GONE);  // 事件分类
        }
        if (user!=null){
            reportName.getTextRightTextNoPadding().setText(user.name);
            reportPhone.getTextRightTextNoPadding().setText(user.mobile);
            reportPart.getTextRightTextNoPadding().setText(user.part_name);
        }
    }
}
