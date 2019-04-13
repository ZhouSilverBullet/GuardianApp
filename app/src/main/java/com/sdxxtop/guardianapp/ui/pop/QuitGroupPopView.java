package com.sdxxtop.guardianapp.ui.pop;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
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

public class QuitGroupPopView extends PopupWindow {

    private LayoutInflater inflater;
    private View viewLayout;
    private Activity activity;
    private View view;
    private TextView quitGroup;
    private TextView quitDec;
    private Runnable runnable;

    public QuitGroupPopView(Activity activity, View viewLayout, String quitText, String quitDec) {
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

        view = inflater.inflate(R.layout.pop_quit_grup_view, null);
        setContentView(view);

        quitGroup = (TextView) view.findViewById(R.id.quit_group);
        quitDec = (TextView) view.findViewById(R.id.quit_dec);
        quitGroup.setText(quitTextValue);
        if (TextUtils.isEmpty(quitDecValue)) {
            quitDec.setVisibility(View.GONE);
        } else {
            quitDec.setText(quitDecValue);
        }
        quitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runnable != null) {
                    runnable.run();
                }
                dismiss();
            }
        });

        view.findViewById(R.id.exit_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    public void setQuitListener(Runnable runnable) {
        this.runnable = runnable;
    }

    public void toWindowBackground(Activity activity, float alpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        activity.getWindow().setAttributes(lp);
    }
}
