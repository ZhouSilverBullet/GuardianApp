package com.sdxxtop.guardianapp.ui.pop;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sdxxtop.guardianapp.R;


/**
 * Created by Administrator on 2018/8/20.
 */

public class SelectMapPopView extends PopupWindow {

    private LayoutInflater inflater;
    private View viewLayout;
    private Activity activity;
    private View view;
    private TextView quitGroup;

    public SelectMapPopView(Activity activity, View viewLayout, String quitText, String quitDec) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.viewLayout = viewLayout;
        initView(quitText, quitDec);
    }

    private void initView(String quitTextValue, String quitDecValue) {
        setTouchable(true);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        view = inflater.inflate(R.layout.pop_select_map_view, null);
        setContentView(view);

        quitGroup = (TextView) view.findViewById(R.id.quit_group);
        quitGroup.setText(quitTextValue);
        quitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectMapClickListener != null) {
                    mSelectMapClickListener.clickToGaode();
                }
                dismiss();
            }
        });

        TextView exitQuitText = view.findViewById(R.id.exit_quit);
        exitQuitText.setText(quitDecValue);

        exitQuitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectMapClickListener != null) {
                    mSelectMapClickListener.clickToBaidu();
                }
                dismiss();
            }
        });

        setAnimationStyle(R.style.ActionSheetDialogAnimation);
        showAtLocation(viewLayout, Gravity.BOTTOM, 0, 0);

        toWindowBackground(activity, 0.6f);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                toWindowBackground(activity, 1f);
            }
        });
    }

    private SelectMapClickListener mSelectMapClickListener;

    public void setSelectMapClickListener(SelectMapClickListener selectMapClickListener) {
        mSelectMapClickListener = selectMapClickListener;
    }

    public interface SelectMapClickListener {
        void clickToGaode();
        void clickToBaidu();
    }

    public void toWindowBackground(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }
}
