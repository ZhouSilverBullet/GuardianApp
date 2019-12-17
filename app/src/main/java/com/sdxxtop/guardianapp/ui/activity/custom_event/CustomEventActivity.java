package com.sdxxtop.guardianapp.ui.activity.custom_event;

import android.view.View;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.model.bean.EventStreamReportBean;
import com.sdxxtop.guardianapp.presenter.CustomEventPresenter;
import com.sdxxtop.guardianapp.presenter.contract.CustomEventContract;
import com.sdxxtop.guardianapp.ui.widget.TextAndTextView;

import butterknife.BindView;

public class CustomEventActivity extends BaseMvpActivity<CustomEventPresenter> implements CustomEventContract.IView {

    @BindView(R.id.report_name)
    TextAndTextView reportName;
    @BindView(R.id.report_phone)
    TextAndTextView reportPhone;
    @BindView(R.id.report_part)
    TextAndTextView reportPart;

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
        mPresenter.loadData(streamId);
    }

    @Override
    public void setData(EventStreamReportBean bean) {
        //设置上报人信息
        EventStreamReportBean.UserBean user = bean.user;
        EventStreamReportBean.ReportPathBean reportPath = bean.reportPath;
        reportName.setVisibility(reportPath.username == 1 ? View.VISIBLE : View.GONE);
        reportPhone.setVisibility(reportPath.userPhone == 1 ? View.VISIBLE : View.GONE);
        reportPart.setVisibility(reportPath.userPart == 1 ? View.VISIBLE : View.GONE);
    }
}
