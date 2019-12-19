package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ERDSecondPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.ui.widget.CustomVideoImgSelectView;
import com.sdxxtop.guardianapp.ui.widget.EvaluateSelectView;
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


    //	事件状态 3、已解决 4、验收通过 5验收不通过 6无法解决(以弹窗形式展示)
    public static final int TYPE_SOLVE = 3;  // 3、已解决
    public static final int TYPE_CHACK_SUCCSESS = 4;  //4、验收通过
    public static final int TYPE_CHACK_FAILE = 5;  //5验收不通过

    @BindView(R.id.et_num_content1)
    NumberEditTextView etNumContent1;
    @BindView(R.id.et_num_content_2)
    NumberEditTextView etNumContent2;
    @BindView(R.id.btn_push)
    Button btnPush;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.cvisv_view)
    CustomVideoImgSelectView cvisvView;
    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.ll_jiejue_layout)
    LinearLayout llJiejueLayout;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    /*******评价*******/
    @BindView(R.id.ll_pinglun)
    LinearLayout llPinglun;
    @BindView(R.id.esv_view)
    EvaluateSelectView esvView;
    @BindView(R.id.et_num_content)
    NumberEditTextView etNumContent;


    private String mEventId;
    private int mEventType;
    private boolean isMastNeed;  //内容是否必填

    private int isShowImgSelect;  // 是否能选择图片
    private int maxSelectCount;   // 选择图片的最大数量
    private int maxTextDesc;      // 描述的字数限制
    private int isShowPinlun;     // 是否显示评论
    private int pinlunTextCount;     // 评论字数限制


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
        showToast(error);
        hideLoadingDialog();
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mEventId = intent.getStringExtra("eventId");
            mEventType = intent.getIntExtra("eventType", TYPE_REPORT);

            isShowImgSelect = intent.getIntExtra("isShowImgSelect", 2);
            maxSelectCount = intent.getIntExtra("maxSelectCount", 3);
            maxTextDesc = intent.getIntExtra("maxTextDesc", 200);
            isShowPinlun = intent.getIntExtra("isShowPinlun", 2);
            pinlunTextCount = intent.getIntExtra("pinlunTextCount", 200);
        }
        // 设置评论
        llPinglun.setVisibility(isShowPinlun == 1 ? View.VISIBLE : View.GONE);
        etNumContent.setMaxLength(pinlunTextCount);

        etNumContent2.setEditHint(" ");

        switch (mEventType) {
            case TYPE_SOLVE: // 3、已解决
                llJiejueLayout.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                cvisvView.tvTitle.setText("已解决现场照片/视频");
                cvisvView.tvDesc.setText("");
                tvRemark.setText("解决问题的简要描述:");
                etNumContent1.setVisibility(View.GONE);
                tvTitle.setTitleValue("解决反馈");
                etNumContent2.setMaxLength(maxTextDesc);
                isMastNeed = false;
                break;
            case TYPE_CHACK_SUCCSESS://4、验收通过
                tvRemark.setText("简要");
                etNumContent1.setEditHint("非必填");
                tvTitle.setTitleValue("验收通过");
                isMastNeed = false;
                break;
            case TYPE_CHACK_FAILE://5验收不通过
                tvRemark.setText("验收不通过原因");
                tvTitle.setTitleValue("验收不通过");
                etNumContent1.setEditHint("必填");
                isMastNeed = true;
                break;
        }
        cvisvView.setVisibility(isShowImgSelect == 1 ? View.VISIBLE : View.GONE);
        cvisvView.setMaxImgCount(9);
        etNumContent1.setMaxLength(maxTextDesc);
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

        if (isShowImgSelect == 1) {
            if (imagePushPath.size() + videoPushPath.size() < maxSelectCount) {
                showToast("需要提供" + maxSelectCount + "个以上图片或者视频");
                return;
            }
        }

        String editValue = etNumContent1.getEditValue();
        String editValue2 = etNumContent2.getEditValue();

        if (mEventType == 3) {
            if (TextUtils.isEmpty(editValue2) && isMastNeed) {
                showToast("请填写编辑内容");
                return;
            }
        } else {
            if (TextUtils.isEmpty(editValue) && isMastNeed) {
                showToast("请填写编辑内容");
                return;
            }
        }

        int pinglunStatus = esvView.getCurrentSelectItem();
        String pinglunStr = etNumContent.getEditValue();

        mPresenter.modify(
                mEventId, mEventType, mEventType == 3 ? editValue2 : editValue,
                imagePushPath, videoPushPath,
                pinglunStatus,
                pinglunStr
        );
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
