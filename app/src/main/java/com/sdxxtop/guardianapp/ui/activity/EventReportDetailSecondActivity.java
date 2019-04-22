package com.sdxxtop.guardianapp.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ERDSecondPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SelectHoriPhotoView;
import com.sdxxtop.guardianapp.ui.widget.SingleDataView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;

public class EventReportDetailSecondActivity extends BaseMvpActivity<ERDSecondPresenter> implements ERDSecondContract.IView {

    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.iv_time_more)
    ImageView ivTimeMore;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.et_num_content)
    NumberEditTextView etNumContent;
    @BindView(R.id.shpv_view)
    SelectHoriPhotoView mShpvView;
    @BindView(R.id.btn_push)
    Button btnPush;
    private SingleDataView mSingleDataView;
    private String mEventId;
    private ArrayList<String> mList;

    @Override
    protected int getLayout() {
        return R.layout.activity_eventa_report_detail_second;
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
        super.initVariables();
        Intent intent = getIntent();
        if (intent != null) {
            mEventId = intent.getStringExtra("eventId");
        }
    }

    @OnClick({R.id.iv_time_more, R.id.tv_select, R.id.btn_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_time_more:
            case R.id.tv_select:
                showSelect();
                break;
            case R.id.btn_push:
                push();
                break;
        }
    }

    private void push() {
        if (mList == null) {
            showToast("请选择验收通过类型");
            return;
        }

        String editValue = etNumContent.getEditValue();
        if (TextUtils.isEmpty(editValue)) {
//            showToast("请填写编辑内容");
//            return;
            editValue = "";
        }

        String selectType = tvSelect.getText().toString().trim();
        int i = mList.indexOf(selectType) + 4;

        List<File> imagePushPath = mShpvView.getImagePushPath();

        mPresenter.modify(mEventId, i, editValue, imagePushPath);
    }

    private void showSelect() {
        if (mSingleDataView == null) {
            mList = new ArrayList<>();
            mList.add("验证通过");
            mList.add("验证不通过");

            mSingleDataView = new SingleDataView(this, mList);
        }

        mSingleDataView.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tvSelect.setText(item);
            }
        });

        mSingleDataView.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mShpvView != null) {
            mShpvView.callActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void modifyRefresh() {
        showToast("提交成功");
        setResult(200);
        finish();
    }
}
