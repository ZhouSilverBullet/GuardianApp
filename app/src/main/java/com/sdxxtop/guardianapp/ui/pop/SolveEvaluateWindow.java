package com.sdxxtop.guardianapp.ui.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.ui.widget.EvaluateSelectView;
import com.sdxxtop.guardianapp.ui.widget.NumberEditTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ER: EventReport的缩写
 */
public class SolveEvaluateWindow extends PopupWindow {

    @BindView(R.id.rl_containor)
    LinearLayout rlContainor;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_num_content)
    public NumberEditTextView etNumContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.esv_view)
    EvaluateSelectView esv_view;

    private Activity mActivity;
    private View mView;

    public SolveEvaluateWindow(Activity activity) {
        mActivity = activity;
        initUI();
        defaultStyle(activity);
    }

    public SolveEvaluateWindow(Activity activity, View view) {
        mActivity = activity;
        this.mView = view;
        initUI();
        defaultStyle(activity);
    }

    private void initUI() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_solve_evaluate_window, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        etNumContent.setMaxLength(100);
        etNumContent.setEditHint(" ");

        if (mView != null) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SolveEvaluateWindow.this.dismiss();
                }
            });
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                setShowBackground(1.f);
                if (mView != null) {
                    mView.setVisibility(View.GONE);
                }
                SolveEvaluateWindow.this.dismiss();
            }
        });
    }

    public void show(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);

//        setShowBackground(0.6f);
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
        }
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

    @SuppressLint("WrongConstant")
    private void defaultStyle(Context context) {
        int weight = context.getResources().getDisplayMetrics().widthPixels;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        setWidth(weight);
        setHeight(height);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setAnimationStyle(R.style.ActionSheetDialogAnimation);
    }

    private void setShowBackground(float alpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = alpha;
        mActivity.getWindow().setAttributes(lp);
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                //提交确定
                String editValue = etNumContent.getEditValue();
                if (mListener != null) {
                    mListener.onButtonClick(editValue,esv_view.getCurrentSelectItem());
                }
                break;
        }
    }

    private OnConfirmClick mListener;

    public void setOnConfirmClick(OnConfirmClick listener) {
        this.mListener = listener;
    }

    public interface OnConfirmClick {
        void onButtonClick(String str,int statusStr);
    }

}
