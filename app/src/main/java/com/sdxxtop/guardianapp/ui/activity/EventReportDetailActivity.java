package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.EventReportDetailPresenter;
import com.sdxxtop.guardianapp.presenter.contract.EventReportDetailContract;
import com.sdxxtop.guardianapp.ui.dialog.IosAlertDialog;
import com.sdxxtop.guardianapp.ui.pop.ERCheckResultWindow;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportDetailActivity extends BaseMvpActivity<EventReportDetailPresenter> implements EventReportDetailContract.IView {

    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_check_method)
    TextView tvCheckMethod;
    @BindView(R.id.tv_happen)
    TextView tvHappen;
    @BindView(R.id.tv_report_path)
    TextView tvReportPath;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_check_success)
    Button btnCheckSuccess;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter();
    }

    @Override
    public void showError(String error) {
        //数据展示
        tvContentTitle.setText("");
//        rv
        tvTime.setText("2019...");
        tvCheckMethod.setText("巡逻");
        tvHappen.setText("罗西街道");
        tvReportPath.setText("环保局");
        tvDescription.setText("事件简要描述：" + "N内容");
    }

    @OnClick({R.id.btn_check_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_success:
                //1.已经派发,确定的时候
//                showConfirmDialog();
                //2.已经反馈,然后弹出验收结果
//                showPop();
                skipSecondPush();
                break;
        }
    }

    private void skipSecondPush() {
        Intent intent = new Intent(this, EventReportDetailSecondActivity.class);
        startActivity(intent);
    }

    private void showConfirmDialog() {
        IosAlertDialog builder = new IosAlertDialog(this)
                .builder();
        builder.setHeightMsg("确认事件上报问题已经解决了吗？")
                .setPositiveButton("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


    }

    //2.已经反馈,然后弹出验收结果
    private void showPop() {
        ERCheckResultWindow erCheckResultWindow = new ERCheckResultWindow(this);
        erCheckResultWindow.show(getLayout());
    }
}
