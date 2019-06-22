package com.sdxxtop.guardianapp.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;
import com.sdxxtop.guardianapp.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ER: EventReport的缩写
 */
public class ERCheckResultWindow extends PopupWindow {
    @BindView(R.id.iv_time_more)
    ImageView ivTimeMore;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.et_num_content)
    NumberEditTextView etNumContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.rl_containor)
    RelativeLayout rlContainor;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private Activity mActivity;
    private View mView;

    public ERCheckResultWindow(Activity activity) {
        mActivity = activity;
        initUI();
        defaultStyle(activity);
    }
    public ERCheckResultWindow(Activity activity,View view) {
        mActivity = activity;
        this.mView = view;
        initUI();
        defaultStyle(activity);
    }

    private void initUI() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_er_check_result_window, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        etNumContent.setMaxLength(100);
        etNumContent.setEditHint("必填");

        if (mView!=null){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ERCheckResultWindow.this.dismiss();
                }
            });
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                setShowBackground(1.f);
                mView.setVisibility(View.GONE);
                ERCheckResultWindow.this.dismiss();
            }
        });

    }

    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);

//        setShowBackground(0.6f);
        mView.setVisibility(View.VISIBLE);
    }

    public void show(int parentLayoutId) {
        show(mActivity.getLayoutInflater().inflate(parentLayoutId, null));
    }

    /**
     * 上报详情无法解决
     *
     * @param parentLayoutId
     * @param isShowHead
     */
    public void show(int parentLayoutId, boolean isShowHead) {
        rlContainor.setVisibility(isShowHead ? View.VISIBLE : View.GONE);
        tvTitle.setText("无法解决原因");
        btnConfirm.setText("提交");
        etNumContent.setEditHint(" ");
        show(mActivity.getLayoutInflater().inflate(parentLayoutId, null));
    }

    private void defaultStyle(Context context) {
        int weight = context.getResources().getDisplayMetrics().widthPixels;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setWidth(weight);
        setHeight(height);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setAnimationStyle(R.style.ActionSheetDialogAnimation);
    }

    private void setShowBackground(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    @OnClick({R.id.iv_time_more, R.id.tv_select, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_time_more:
            case R.id.tv_select:
                //弹出选项
                break;
            case R.id.btn_confirm:
                //提交确定

                String editValue = etNumContent.getEditValue();
                if (TextUtils.isEmpty(editValue)){
                    UIUtils.showToast("请描述无法解决原因");
                    return;
                }

                if (mListener != null) {
                    mListener.onButtonClick(editValue);
                }
                break;
        }
    }

    private OnConfirmClick mListener;

    public void setOnConfirmClick(OnConfirmClick listener) {
        this.mListener = listener;
    }

    public interface OnConfirmClick {
        void onButtonClick(String str);
    }

}
