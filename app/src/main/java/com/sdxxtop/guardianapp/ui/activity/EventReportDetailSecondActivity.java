package com.sdxxtop.guardianapp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseMvpActivity;
import com.sdxxtop.guardianapp.presenter.ERDSecondPresenter;
import com.sdxxtop.guardianapp.presenter.contract.ERDSecondContract;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.ui.widget.SingleDataView;
import com.sdxxtop.guardianapp.ui.widget.TitleView;

import java.util.ArrayList;

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
    @BindView(R.id.btn_push)
    Button btnPush;
    private SingleDataView mSingleDataView;

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

    }

    @OnClick({R.id.iv_time_more, R.id.tv_select, R.id.btn_push})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_time_more:
            case R.id.tv_select:
                showSelect();
                break;
            case R.id.btn_push:
                break;
        }
    }

    private void showSelect() {
        if (mSingleDataView == null) {
            ArrayList<String> list = new ArrayList<>();
            list.add("验证通过");
            list.add("验证不通过");

            mSingleDataView = new SingleDataView(this, list);
        }

        mSingleDataView.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tvSelect.setText(item);
            }
        });

        mSingleDataView.show();
    }
}
