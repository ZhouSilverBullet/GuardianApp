package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ERDSecondPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class EventReportDetailSecondActivity extends BaseMvpActivity<ERDSecondPresenter> implements ERDSecondContract.IView {

    //彻底检查完成
    public static final int TYPE_REPORT = 1;
    //反馈完成
    public static final int TYPE_FINISH = 2;


    //	事件状态 3、已解决 4、验收通过 5验收不通过 6无法解决
    public static final int TYPE_SOLVE = 3;  // 3、已解决
    public static final int TYPE_UNSOLVE = 6;  // 6无法解决
    public static final int TYPE_CHACK_SUCCSESS = 4;  //4、验收通过
    public static final int TYPE_CHACK_FAILE = 5;  //5验收不通过


    @BindView(R.id.et_num_content)
    NumberEditTextView etNumContent;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.tv_title)
    TitleView tvTitle;

    private String mEventId;
    private int mEventType;
    private boolean isMastNeed;  //内容是否必填

    @Override
    protected int getLayout() {
        return R.layout.activity_event_report_detail_second;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showError(String error) {
        hideLoadingDialog();
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mEventId = intent.getStringExtra("eventId");
            mEventType = intent.getIntExtra("eventType", TYPE_REPORT);
        }
        etNumContent.setMaxLength(200);
        switch (mEventType) {
            case TYPE_SOLVE: // 3、已解决
                tvRemark.setText("解决问题的简要描述");
                etNumContent.setEditHint("非必填");
                tvTitle.setTitleValue("已解决");
                isMastNeed = false;
                break;
            case TYPE_CHACK_SUCCSESS://4、验收通过
                tvRemark.setText("简要");
                etNumContent.setEditHint("非必填");
                tvTitle.setTitleValue("验收通过");
                isMastNeed = false;
                break;
            case TYPE_CHACK_FAILE://5验收不通过
                tvRemark.setText("验收不通过原因");
                tvTitle.setTitleValue("验收不通过");
                etNumContent.setEditHint("必填");
                isMastNeed = true;
                break;
        }
    }

    @OnClick({R.id.btn_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_push:
                push();
                break;
        }
    }

    private void push() {

        List<File> imagePushPath = cvisvView.getImageOrVideoPushPath(1);
        List<File> videoPushPath = cvisvView.getImageOrVideoPushPath(2);

        if (imagePushPath.size() == 0 && videoPushPath.size() == 0) {
            showToast("请选择照片或视频");
            return;
        }

        String editValue = etNumContent.getEditValue();
        if (TextUtils.isEmpty(editValue) && isMastNeed) {
            showToast("请填写编辑内容");
            return;
        }
        showLoadingDialog();
        mPresenter.modify(mEventId,mEventType,editValue,imagePushPath,videoPushPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cvisvView.callActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void modifyRefresh() {
        showLoadingDialog();
        showToast("提交成功");
        setResult(200);
        finish();
    }
}
